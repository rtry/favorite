package sicau.edu.cn.favorite;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.server.Server;

import sicau.edu.cn.favorite.constant.JettyConstant;

public class ExecBrowserThread implements Runnable {

	Server server;

	public ExecBrowserThread(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		System.out.println("程序启动..打开游览器");
		try {
			URI uri = new URI(JettyConstant.indexUrl);
			Desktop.getDesktop().browse(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
