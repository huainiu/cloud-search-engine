//---------------------------------------------------------
//LSEDeamonクラス
//
//LSEのデーモンプログラムを実行する
//---------------------------------------------------------
package localsearchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import localsearchengine.config.XMLConfig;
import localsearchengine.demon.Daemon;

public class LSEDaemon2 {

	/**
	 * 実行メインプログラム
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		long time = System.currentTimeMillis();

		//Deamon
		Daemon daemon = null;

		try {
			//LSEのconfig.xml読み込み
			Map<String, Object> xml = configData("src/localsearchengine/config/lse-config.xml");
			System.out.println(xml.get("dir"));
			System.out.println(xml.get("location"));
			System.out.println(xml.get("nodes"));
			System.out.println(xml.get("solr"));
			//Daemon実行
			daemon = new Daemon();
			//初期設定
			daemon.dir((String) xml.get("dir"));
			daemon.location((Map<String, String>) xml.get("location"));
			daemon.addNode((List<String>) xml.get("nodes"));
			daemon.lseSolr((Map<String, String>) xml.get("solr"));
			//solrConfig作成
			//daemon.solrConfigWriter("core0");
			//daemon.solrConfigWriter("core1");
			//daemon.solrConfigWriter("core2");
			//スタート
			daemon.start();
			//無限ループ
			while (true) {}
			//Thread.sleep(3600 * 1000L);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//デーモンのストップ
			daemon.stop();
		}

		System.out.println(System.currentTimeMillis() - time);
	}

	/**
	 * configDataメソッド
	 *
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static Map<String, Object> configData(String path) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		//LSEクラスタを制御するための設定ファイルを読み込む
		XMLConfig config = new XMLConfig(path);
		//ディレクトリ
		result.put("dir", config.getElement("dir"));
		//location
		result.put("location", config.getHost2Port("location"));
		//nodes
		result.put("nodes", config.getNodes("node"));
		//lse
		result.put("solr", config.getHost2Port("solr"));

		return result;
	}
}