package iii.aihub.route.processor.article;

import iii.aihub.entity.article.Article;
import iii.aihub.entity.member.Member;
import iii.aihub.entity.solution.Solution;
import iii.aihub.entity.tag.Tag;
import iii.aihub.entity.vender.Vender;
import iii.aihub.helper.ArticleHelper;
import iii.aihub.helper.MemberHelper;
import iii.aihub.helper.TagHelper;
import iii.aihub.helper.VenderHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ModifyArticleProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ArticleHelper articleHelper;

    @Autowired
    MemberHelper memberHelper;

    @Autowired
    VenderHelper venderHelper;

    @Autowired
    TagHelper tagHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String dataId = InputParameterUtils.getStringParameter(data, "data_id");
        String title = InputParameterUtils.getStringParameter(data, "title");
        String content = InputParameterUtils.getStringParameter(data, "content");
        String memberId = InputParameterUtils.getStringParameter(data, "member_id");
        String url = InputParameterUtils.getStringParameter(data, "url");
        String type = InputParameterUtils.getStringParameter(data, "type");
        DateTime publishDatetime = InputParameterUtils.getDateTimeParameter(data, "publish_date");
        DateTime create = DateTime.now();
        DateTime updated = DateTime.now();
        String isDisplay = InputParameterUtils.getStringParameter(data, "is_display");
        //Integer venderId = InputParameterUtils.getIntegerParameter(data, "vender_id");
        String firstImgUrl = InputParameterUtils.getStringParameter(data, "first_img_url");
        String solutions = InputParameterUtils.getStringParameter(data, "solutions");
        String tagIds = InputParameterUtils.getStringParameter(data, "tag_ids");
        String isDelete = "N";

        Member author = null;
        try {
            author = memberHelper.getMember(memberId);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        logger.info("author: "+author);

        if (publishDatetime == null){
            throw new Exception("publish datetime is null");
        }

        String[] solutionStringArray = null;
        List<Solution> solutionList = new ArrayList<>();
        Solution solution = null;
        if (solutions != null){
            solutionStringArray = StringUtils.split(solutions, ':');
            logger.info("solution string array: "+solutionStringArray);
            for (String solutionId : solutionStringArray){
                solution = new Solution();
                logger.info("solution id: "+solutionId);
                solution.solutionId = solutionId;
                solutionList.add(solution);
            }
        }
        logger.info("article solutions: "+solutionList);

        //-- tag
        List<Tag> tags = new ArrayList<>();
        logger.info("tag ids: "+tagIds);
        if (tagIds != null) {
            String[] tagArray = StringUtils.split(tagIds, ",");
            Integer tagId;
            Tag tag;
            for (String tagString : tagArray) {
                tagId = Integer.parseInt(tagString);
                tag = tagHelper.getTag(tagId);
                if (tag != null) {
                    tags.add(tag);
                }
            }
        }

        Article article = new Article();
        article.author =  author.firstName + " - "+author.lastName;
        article.content = content;
        article.created = DateTime.now().toDate();
        article.dataId = articleHelper.generateArticleId(url);
        article.title = title;
        article.updated = updated.toDate();
        article.isDelete = isDelete;
        article.memberId = memberId;
        article.publishDatetime = publishDatetime.toDate();
        article.url = url;
        article.imgUrl = firstImgUrl;
        article.isDisplay = isDisplay;
        article.created = create.toDate();
        article.solutionList = solutionList;
        article.type = type;
        article.dataId = dataId;
        article.tagList = tags;
        int result = articleHelper.modifyArticle(article);

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(result);

    }
}
