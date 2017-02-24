package com.liuruichao.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * ZooKeeperPropertyPlaceholderConfigurer
 *
 * @author liuruichao
 * @date 15/11/27 上午10:40
 */
public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final Logger logger = Logger.getLogger(ZooKeeperPropertyPlaceholderConfigurer.class);
    private static String propPath;
    private static CuratorFramework curator;

    static {
        InputStream in = ZooKeeperFactory.class.getClassLoader()
                .getResourceAsStream("zoo.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            String connStr = p.getProperty("zoo.connStr");
            int maxRetries = Integer.valueOf(p.getProperty("zoo.maxRetries"));
            int baseSleepTimems = Integer.valueOf(p.getProperty("zoo.baseSleepTimems"));
            propPath = p.getProperty("zoo.propPath");
            logger.debug("from zookeeper load application config, path : " + propPath);
            curator = ZooKeeperFactory.newInstance(baseSleepTimems, maxRetries, connStr);
        } catch (IOException e) {
            logger.error("ZooKeeperPropertyPlaceholderConfigurer static init method error : ", e);
        }
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        try {
            fillProperties(props, getData(propPath));
        } catch (Exception e) {
            logger.error("ZooKeeperPropertyPlaceholderConfigurer.processProperties()", e);
        }
        super.processProperties(beanFactoryToProcess, props);
    }

    private byte[] getData(String path) throws Exception {
        if (curator.checkExists().forPath(path) == null) {
            throw new RuntimeException("Path " + path + " does not exists.");
        }
        return curator.getData().forPath(path);
    }

    private void fillProperties(Properties props, byte[] data) throws UnsupportedEncodingException {
        String cfg = new String(data, "UTF-8");
        if (StringUtils.isNotBlank(cfg)) {
            String[] cfgItems = StringUtils.split(cfg, ",");
            for (String cfgItem : cfgItems) {
                String[] item = cfgItem.split("=");
                props.put(item[0], item[1]);
            }
        }
    }
}
