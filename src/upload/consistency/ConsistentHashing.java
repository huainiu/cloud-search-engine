//---------------------------------------------------------
//ConsistentHashing クラス
//
//LSEのSolrサーバにスケーラブルに文書を格納するためのハッシュ方法
//---------------------------------------------------------
package upload.consistency;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ConsistentHashing {

	//-----------------------------------------------------
	//プロパティ
	//-----------------------------------------------------

	//LSE(Solrサーバ)の仮想ノード付き格納リング
	private static NavigableMap<BigInteger, String> circle = new TreeMap<BigInteger, String>();
	//LSE(Solrサーバ)の物理的ノード格納リング
	private static NavigableMap<BigInteger, String> continuum = new TreeMap<BigInteger, String>();

	//レプリケーション(仮想ノード)数
	private static int REPLICANTS = 100;

	//-----------------------------------------------------
	//コンストラクタ
	//-----------------------------------------------------

	/**
	 * コンストラクタ(デフォルト)
	 */
	public ConsistentHashing() {

	}

	/**
	 * コンストラクタ(仮想ノード数を指定する)
	 *
	 * @param replicants
	 */
	public ConsistentHashing(int replicants) {
		ConsistentHashing.REPLICANTS = replicants;
	}

	//-----------------------------------------------------
	//publicメソッド
	//-----------------------------------------------------

	/**
	 * addNodeメソッド (ノードの追加する)
	 *
	 * @param node ノードのアドレスを指定する
	 */
	public void addNode(String node) {
		//物理ノードに追加する
		continuum.put(getHash(node), node);
		//仮想ノードに追加する
		for (int i = 0; i < REPLICANTS; i++) {
			circle.put(getHash(node + "_" + i), node);
		}
	}

	/**
	 * addNodeメソッド (複数のノードを一遍に登録する)
	 *
	 * @param nodes
	 */
	public void addNode(String... nodes) {
		for (int i = 0; i < nodes.length; i++) {
			//物理ノードに追加する
			continuum.put(getHash(nodes[i]), nodes[i]);
			//仮想ノードに追加する
			for (int j = 0; j < REPLICANTS; j++) {
				circle.put(getHash(nodes[i] + "_" + j), nodes[i]);
			}
		}
	}

	/**
	 * addNodeメソッド (Listでノードを一遍に登録する)
	 *
	 * @param nodes
	 */
	public void addNode(List<String> nodes) {
		for (String node : nodes) {
			//物理ノードに追加する
			continuum.put(getHash(node), node);
			//仮想ノードに追加する
			for (int i = 0; i < REPLICANTS; i++) {
				circle.put(getHash(node + "_" + i), node);
			}
		}
	}

	/**
	 * delNodeメソッド (ノードの削除する)
	 *
	 * @param node
	 */
	public void delNode(String node) {
		//物理ノードに追加する
		continuum.remove(getHash(node));
		//仮想ノードに追加する
		for (int i = 0; i < REPLICANTS; i++) {
			circle.remove(getHash(node + "_" + i));
		}
	}

	/**
	 * delNodeメソッド (複数のノードを一遍に削除する)
	 *
	 * @param nodes
	 */
	public void delNode(String... nodes) {
		for (int i = 0; i < nodes.length; i++) {
			//物理ノードに追加する
			continuum.remove(getHash(nodes[i]));
			//仮想ノードに追加する
			for (int j = 0; j < REPLICANTS; j++) {
				circle.remove(getHash(nodes[i] + "_" + j));
			}
		}
	}

	/**
	 * nextNodeメソッド
	 *
	 * @param key
	 * @return
	 */
	public String nextNode(String key) {
		BigInteger hash = continuum.higherKey(getHash(key));
		if (hash == null) {
			return continuum.get(continuum.firstKey());
		} else {
			return continuum.get(hash);
		}
	}

	/**
	 * prevNodeメソッド
	 *
	 * @param key
	 * @return
	 */
	public String prevNode(String key) {
		BigInteger hash = continuum.lowerKey(getHash(key));
		if (hash == null) {
			return continuum.get(continuum.lastKey());
		} else {
			return continuum.get(hash);
		}
	}

	public boolean isNode(String key) {
		return continuum.containsValue(key);
	}

	/**
	 * searchNodeメソッド (格納する文書のアドレスを調べる)
	 *
	 * @param key
	 * @return
	 */
	public String searchNode(String key) {
		return search(circle, getHash(key));
	}

	/**
	 * nodeListメソッド (ノードリスト一覧を表示する)
	 */
	public void nodeList() {
		System.out.println(continuum);
	}

	//-----------------------------------------------------
	//privateメソッド
	//-----------------------------------------------------

	/**
	 * getHashメソッド
	 *
	 * @param value
	 * @return
	 */
	private static BigInteger getHash(String value) {
		byte[] byteHash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byteHash = digest.digest(value.getBytes());
			return new BigInteger(byteHash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BigInteger(byteHash);
	}

	/**
	 * searchメソッド
	 *
	 * @param circle
	 * @param rec
	 * @return
	 */
	private static String search(NavigableMap<BigInteger, String> circle, BigInteger rec ) {
		BigInteger key = circle.ceilingKey(rec);
		if (key == null) {
			return (String) circle.get(circle.firstKey());
		} else {
			return (String) circle.get(key);
		}
	}

}
