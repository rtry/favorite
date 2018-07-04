/**    
 * 文件名：BlogType.java    
 *    
 * 版本信息：    
 * 日期：2018年7月2日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.html.wash;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 类名称：BlogType <br>
 * 类描述: 所有已经<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月2日 上午11:33:09 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月2日 上午11:33:09 <br>
 * 修改备注:
 * @version
 * @see
 */
public enum BlogType {

	sina("blog.sina.com.cn"),

	csdn("blog.csdn.net"),

	iteye("iteye.com"),

	cto_51("51cto.com"),

	jb_51("jb51.net"),

	oschina("oschina.net"),

	blog_163("blog.163.com"),

	ibm("ibm.com"),

	tuicool("tuicool.com"),

	jianshu("jianshu.com"),

	linuxidc("linuxidc.com"),

	cnblogs("cnblogs.com");

	private String domainName;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	private BlogType(String domainName) {
		this.domainName = domainName;
	}

	public static BlogType getByDomainName(String name) {

		BlogType iteye = BlogType.iteye;
		BlogType cto_51 = BlogType.cto_51;
		BlogType blog_163 = BlogType.blog_163;
		BlogType oschina = BlogType.oschina;

		if (name.endsWith(iteye.getDomainName()))
			return iteye;
		else if (name.endsWith(cto_51.getDomainName()))
			return cto_51;
		else if (name.endsWith(oschina.getDomainName()))
			return oschina;
		else if (name.endsWith(blog_163.getDomainName()))
			return blog_163;

		BlogType[] types = BlogType.values();
		for (BlogType type : types) {
			if (type.getDomainName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * helpFul 简介
	 * @param page
	 * @param type
	 * @return String
	 * @Exception 异常描述
	 */
	public static String helpFul(HtmlPage page, BlogType type) {
		Document doc = page.getDoc();
		String text = null;
		try {
			switch (type) {
			case iteye:
				Element iteye = doc.selectFirst("#blog_content");
				Elements iteyeEls = iteye.select("pre");
				iteyeEls.forEach(e -> {
					e.remove();
				});
				if (iteye != null && iteye.hasText())
					text = iteye.text();
				break;

			case csdn:
				Element csdn = doc.selectFirst("article");
				Elements csdnEls = csdn.select("pre");
				csdnEls.forEach(e -> {
					e.remove();
				});
				if (csdn != null && csdn.hasText())
					text = csdn.text();
				break;

			case tuicool:
				Element tuicool = doc.selectFirst(".article_body");
				Elements tuicoolEls = tuicool.select("pre");
				tuicoolEls.forEach(e -> {
					e.remove();
				});
				if (tuicool != null && tuicool.hasText())
					text = tuicool.text();
				break;

			case linuxidc:
				Element linuxidc = doc.selectFirst("#content");
				Elements linuxidcEls = linuxidc.select("pre");
				linuxidcEls.forEach(e -> {
					e.remove();
				});
				if (linuxidc != null && linuxidc.hasText())
					text = linuxidc.text();
				break;

			case ibm:
				Element ibm = doc.selectFirst("#ibm-content-main");
				Elements ibmEls = ibm.select("pre");
				ibmEls.forEach(e -> {
					e.remove();
				});
				if (ibm != null && ibm.hasText())
					text = ibm.text();
				break;

			case sina:
				Element sina = doc.selectFirst(".articalContent");
				Elements sinaEls = sina.select("pre");
				sinaEls.forEach(e -> {
					e.remove();
				});
				if (sina != null && sina.hasText())
					text = sina.text();
				break;

			case jb_51:
				Element jb = doc.selectFirst("#content");
				Elements jbEls = jb.select("pre");
				jbEls.forEach(e -> {
					e.remove();
				});
				if (jb != null && jb.hasText())
					text = jb.text();
				break;

			case cto_51:
				Element cto = doc.selectFirst(".zwnr");
				Elements ctoEls = cto.select("pre");
				ctoEls.forEach(e -> {
					e.remove();
				});
				if (cto != null && cto.hasText())
					text = cto.text();
				break;

			case blog_163:
				Element blog = doc.selectFirst(".nbw-blog");
				Elements blogEls = blog.select("pre");
				blogEls.forEach(e -> {
					e.remove();
				});
				if (blog != null && blog.hasText())
					text = blog.text();
				break;

			case jianshu:
				Element jianshu = doc.selectFirst(".show-content");
				Elements jianshuEls = jianshu.select("pre");
				jianshuEls.forEach(e -> {
					e.remove();
				});
				if (jianshu != null && jianshu.hasText())
					text = jianshu.text();
				break;

			case oschina:
				Element oschina = doc.selectFirst("#articleContent");
				Elements oschinaEls = oschina.select("pre");
				oschinaEls.forEach(e -> {
					e.remove();
				});
				if (oschina != null && oschina.hasText())
					text = oschina.text();
				break;

			case cnblogs:

				Element cnblogs = doc.selectFirst(".postBody");
				Elements cnblogsEls = cnblogs.select("pre");
				cnblogsEls.forEach(e -> {
					e.remove();
				});
				if (cnblogs != null && cnblogs.hasText())
					text = cnblogs.text();
				break;

			default:

				// 全文检索
				// Element def = doc.selectFirst("body");
				// Elements defEls = def.select("pre");
				// defEls.forEach(e -> {
				// e.remove();
				// });
				// if (def != null && def.hasText())
				// text = def.text();

				break;
			}
		} catch (Exception e) {
			return null;
		}

		return text;
	}
}
