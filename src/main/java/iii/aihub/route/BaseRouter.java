package iii.aihub.route;

import com.google.gson.Gson;
import iii.aihub.entity.ErrorMessage;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

@Component
public class BaseRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());

    Gson gson = new Gson();

    private String portNum;

    private String apiIp;

    @Override
    public void configure() throws Exception {

        if (apiIp == null) {
            apiIp = "0.0.0.0";
            logger.info("api ip setting to "+this.apiIp);
        }
        if (portNum == null){
            portNum = "8808";
            logger.info("api port setting to "+this.portNum);
        }

        restConfiguration()
                .component("restlet")
                .host(apiIp)
                .port(portNum)
                .bindingMode(RestBindingMode.json)
                .enableCORS(true)
                .corsHeaderProperty("Access-Control-Allow-Origin", "*")
                .corsHeaderProperty("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, CONNECT, PATCH")
                .corsHeaderProperty("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")
                .corsHeaderProperty("Access-Control-Max-Age", "3600")
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Aihub API").apiProperty("api.version", "1.0.0")
                .apiProperty("cors", "true")
        ;

        /**
         * 例外處理
         */
        onException(Exception.class, java.lang.NullPointerException.class)
                .handled(true)
                .process(exchange -> {
                    logger.info("body in: "+exchange.getIn().getBody().getClass());
                    LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
                    logger.info("data: "+data);
                    Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    String failEndpoint = exchange.getProperty(Exchange.FAILURE_ENDPOINT, String.class);
                    logger.error("fail endpoint: "+failEndpoint);
                    ErrorMessage em = new ErrorMessage(e.getMessage(), 500);
                    logger.error("exception: "+e.toString());
                    logger.error("exception message: "+e.getMessage());
                    logger.error("error output json: "+gson.toJson(em));
                    logger.error(e.getMessage(), e);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
                    exchange.getOut().setBody(gson.toJson(em));
                })
        ;
    }

    public String getPortNum() {
        return portNum;
    }

    public void setPortNum(String portNum) {
        this.portNum = portNum;
    }

    public String getApiIp() {
        return apiIp;
    }

    public void setApiIp(String apiIp) {
        this.apiIp = apiIp;
    }
}
