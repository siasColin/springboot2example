package cn.net.colin;

import cn.net.colin.common.util.RsaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class Springboot2exampleApplicationTests {

	private String privateFilePath = "H:\\auth_key\\id_key_rsa";
	private String publicFilePath = "H:\\auth_key\\id_key_rsa.pub";

	@Test
	public void contextLoads() {
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////		System.out.println(bCryptPasswordEncoder.encode("123456"));
		try{
			RsaUtils.generateKey(publicFilePath, privateFilePath, "colin", 2048);
			System.out.println(RsaUtils.getPublicKey(publicFilePath));
			System.out.println(RsaUtils.getPrivateKey(privateFilePath));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
