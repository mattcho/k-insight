package seung.spring.conf.mybatis;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import seung.spring.conf.arg.SAppProperties;

/**
 * @author seung
 * @desc 2nd datasource
 */
@Configuration
public class SConfigurationDataSource02 {

	@Autowired
	SAppProperties sApplicationProperties;
	
	@Bean(name="ds02",destroyMethod="close")
	public BasicDataSource getBasicDataSource02() {
		
		BasicDataSource basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName(sApplicationProperties.getDriverClassNameDs02());
		basicDataSource.setUrl(sApplicationProperties.getUrlDs02());
		basicDataSource.setUsername(sApplicationProperties.getUserNameDs02());
		basicDataSource.setPassword(sApplicationProperties.getPasswordDs02());
		
		return basicDataSource;
	}
	@Bean(name="ssf02")
	public SqlSessionFactory getSqlSessionFactory02() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(getBasicDataSource02());
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:sql/ds02/*.xml"));
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
//		sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(false);
		return sqlSessionFactory;
	}
	@Bean(name="sst02")
	public SqlSessionTemplate getSqlSessionTemplate02() throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(getSqlSessionFactory02());
		return sqlSessionTemplate;
	}
}
