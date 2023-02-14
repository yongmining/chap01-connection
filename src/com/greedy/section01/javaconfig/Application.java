package com.greedy.section01.javaconfig;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class Application {

	private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String USER ="SYSTEM";
	private static String PASSWORD = "oracle";
	
	public static void main(String[] args) {
	
		/* DB 접속에 관한 환경 설정*/ 
		/* ----------------------------------- 
		 * JdbcTransactionFactory : 수동 커밋
		 * MangedTransactionFactory : 자동 커밋
		 * -----------------------------------
		 * PooledDataSource : ConnectionPool 사용
		 * UnPooleDataSource : ConnectionPool 사용하지 않음
		 * -----------------------------------
		 * */
		Environment environment = 											//환경 정보 이름
				new Environment(" dev", new JdbcTransactionFactory()		//트랜젝션 매니저 종류 결정 (JDBC or NANAGED)
			  , new PooledDataSource(DRIVER, URL, USER, PASSWORD)); 		//ConnectionPool 사용 유무(Pooled or UnPooled)

		/* 생성한 환경 설정 정보를 가지고 마이바티스 설정 객체 생성 */
		Configuration configuration = new Configuration(environment);
		
		/* 설정 객체에 매퍼 등록 */
		configuration.addMapper(Mapper.class);
		
		/* SqlSeesionFactory : SqlsSession 객체를 생성하기 위한 팩토리 역할을 수행하는 인터페이스
		 * SqlSessionFactoryBuilder : SqlSessionFactory 인터페이스 타입의 하위 구현 객체를 생성하기 위한 빌드 역할 수행
		 * build() : 설정에 대한 정보를 담고 잇는 Configuration 타입의 객체 혹은 외부 설정 파일과 연결된 스트림을 매개변수로 전달하면
		 * 			 SqlSessionFacgory 인터페이스 타입의 객체를 반환하는 메소드
		 * */
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		
		/* openSession() : SqlSession 인터페이스 타입의 객체를 반환하는 메소드, boolean 타입을 인자로 전달
		 * false : Connection 인터페이스 타입 객체로 DML 수행 후 auto commit에 대한 옵션을 false로 지정 (권장)
		 * true : Connection 인터페이스 타입 객체로 DML 수행 후 auto commit에 대한 옵션을 true로 지정
		 * */
		SqlSession sqlSession = sqlSessionFactory.openSession(false);
		
		Mapper mapper = sqlSession.getMapper(Mapper.class);
		System.out.println(mapper);
		
		java.util.Date date = mapper.selectSysdate();
		
		System.out.println(date);
		
		sqlSession.close();
		
		
	}

}
