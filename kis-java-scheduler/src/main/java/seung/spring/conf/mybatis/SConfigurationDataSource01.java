package seung.spring.conf.mybatis;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import seung.spring.conf.arg.SAppProperties;

/**
 * @author seung
 * @desc 1st datasource
 */
@Configuration
public class SConfigurationDataSource01 {

	@Autowired
	SAppProperties sApplicationProperties;
	
	@Bean(name="ds01",destroyMethod="close")
	@Primary
	public BasicDataSource buildBasicDataSource01() {
		
		BasicDataSource basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName(sApplicationProperties.getDriverClassNameDs01());
		basicDataSource.setUrl(sApplicationProperties.getUrlDs01());
		basicDataSource.setUsername(sApplicationProperties.getUserNameDs01());
		basicDataSource.setPassword(sApplicationProperties.getPasswordDs01());
		
		return basicDataSource;
	}
	
	@Bean(name="ssf01")
	@Primary
	public SqlSessionFactory buildSqlSessionFactory01() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(buildBasicDataSource01());
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:sql/ds01/*.xml"));
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
//		sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(false);
		return sqlSessionFactory;
	}
	@Bean(name="sst01")
	@Primary
	public SqlSessionTemplate buildSqlSessionTemplate01() throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(buildSqlSessionFactory01());
		return sqlSessionTemplate;
	}
}
