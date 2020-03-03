package iii.aihub.route;

import iii.aihub.helper.MemberHelper;
import iii.aihub.route.processor.member.*;
import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class MemberRouter extends SpringRouteBuilder {

    private static final String RESET_PWD_PAGE_URL = "https://aihub.org.tw/admin/index.php/reset_login_pwd";
    private static final String FORGET_PWD_PAGE_URL = "https://aihub.org.tw/admin/index.php/forgor_pwd";

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ListMemberProcessor listMemberProcessor;

    @Autowired
    AddMemberprocessor addMemberprocessor;

    @Autowired
    ModifyMemberProcessor modifyMemberProcessor;

    @Autowired
    DeleteMemberProcessor deleteMemberProcessor;

    @Autowired
    VerifyMemberProcessor verifyMemberProcessor;

    @Autowired
    LoginCheckMemberProcessor loginCheckMemberProcessor;

    @Autowired
    VerifyForgetPwdEmailProcessor verifyForgetPwdEmailProcessor;

    @Autowired
    VerifyForgetPwdUserProcessor verifyForgetPwdUserProcessor;

    @Autowired
    ResetPwdProcessor resetPwdProcessor;

    @Autowired
    MemberHelper memberHelper;

    @Override
    public void configure() throws Exception {

        rest("/member")
                .enableCORS(true)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
            .post("/list")
                .id("list_member_endpoint")
                .description("列出會員(寫手)")
                .to("direct:list_member")
            .post("/add")
                .id("add_member_endpoint")
                .description("新增會員(寫手)")
                .to("direct:add_member")
            .post("/modify")
                .id("modify_member_endpoint")
                .description("修改會員(寫手)")
                .to("direct:modify_member")
            .post("/delete")
                .id("delete_member_endpoint")
                .description("刪除會員(寫手)")
                .to("direct:delete_member")
            .post("/verify")
                .id("verify_member_endpoint")
                .description("註冊審核會員(寫手)")
                .to("direct:verify_member")
            .post("/login_check")
                .id("login_check_member_endpoint")
                .description("登入檢查")
                .to("direct:login_check_member")
            .post("/verify_forget_pwd_email")
                .id("verify_forget_pwd_email_endpoint")
                .description("忘記密碼 1: 確認email，並寄出確認user 信件")
                .to("direct:verify_forget_pwd_email")
            .get("/vfps")
                .id("verify_forget_pwd_user_endpoint")
                .description("忘記密碼 2: 確認user後，導向重設密碼頁面")
                .to("direct:verify_forget_pwd_user")
            .post("/reset_forget_pwd")
                .id("reset_forget_pwd_endpoint")
                .description("忘記密碼 3: 重新設定密碼")
                .to("direct:reset_forget_pwd")
                ;

        //-- 列出會員(寫手)
        from("direct:list_member")
                .routeId("list_member")
                .process(listMemberProcessor)
                ;

        //-- 新增會員(寫手)
        from("direct:add_member")
                .routeId("add_member")
                .process(addMemberprocessor)
        ;

        //-- 修改會員(寫手)
        from("direct:modify_member")
                .routeId("modify_member")
                .process(modifyMemberProcessor)
        ;

        //-- 刪除會員(寫手)
        from("direct:delete_member")
                .routeId("delete_member")
                .process(deleteMemberProcessor)
        ;

        //-- 註冊審核會員(寫手)
        from("direct:verify_member")
                .routeId("verify_member")
                .process(verifyMemberProcessor)
        ;

        //-- 登入檢查
        from("direct:login_check_member")
                .routeId("login_check_member")
                .process(loginCheckMemberProcessor)
        ;

        //-- 忘記密碼 1: 確認email，並寄出確認user 信件
        from("direct:verify_forget_pwd_email")
                .routeId("verify_forget_pwd_email")
                .process(verifyForgetPwdEmailProcessor)
        ;


        //-- 忘記密碼 2: 確認user後，導向重設密碼頁面
        from("direct:verify_forget_pwd_user")
                .routeId("verify_forget_pwd_user")
                .process(verifyForgetPwdUserProcessor)
                .log("${body}")
                .choice()
                    .when(body().isEqualTo("1"))
                        .to("direct:redirect_to_reset_pwd_page")
                    .otherwise()
                        .to("direct:redirect_to_forget_pwd_page")
                .end()
        ;

        //-- 忘記密碼 2-1: 導向重設密碼頁面
        from("direct:redirect_to_reset_pwd_page")
                .routeId("redirect_to_reset_pwd_page")
                //.log("headers: ${headers}")
                .process(exchange -> {
                    Map<String, Object> headers = exchange.getIn().getHeaders();
                    String memberId = (String)headers.get("member_id");
                    String encryptString = String.valueOf(DateTime.now().getMillis()) + ";" + memberId;
                    String url = RESET_PWD_PAGE_URL+"?m="+ URLEncoder.encode(memberHelper.encrypt(encryptString),"UTF-8");
                    exchange.getOut().setHeaders(headers);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 301);
                    exchange.getOut().setHeader("Location", url);
                })
                ;

        //-- 忘記密碼 2-2: 導向忘記密碼頁面
        from("direct:redirect_to_forget_pwd_page")
                .routeId("redirect_to_forget_pwd_page")
                //.log("${headers}")
                .process(exchange -> {
                    exchange.getOut().setHeaders(exchange.getIn().getHeaders());
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 301);
                    exchange.getOut().setHeader("Location", FORGET_PWD_PAGE_URL);
                })
        ;

        //-- 忘記密碼 3: 重新設定密碼
        from("direct:reset_forget_pwd")
                .routeId("reset_forget_pwd")
                .process(resetPwdProcessor)
        ;

    }
}
