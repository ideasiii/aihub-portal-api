package iii.aihub.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BaseHelper {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;
}
