package jp.go.aist.rtm.RTC.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * <p>キーと値のセットからなるプロパティセットを表します。
 * ストリームへ保管したり、ストリームからロードしたりできます。
 * 各プロパティのキー、およびそれに対応する値は文字列です。<p />
 * 
 * <p>プロパティセットには、デフォルトを指定することができ、
 * 元のプロパティセットで指定されたキーが見つからない場合には、
 * この２番目のプロパティセットが検索されます。<p />
 * 
 */
public class Properties {

    private String name;
    private String value;
    private String default_value;
    private Properties root;
    private Vector<Properties> leaf = new Vector<Properties>();
    private static final String EMPTY = "";
	
    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public Properties() {
        
        this("", "");
    }
    
    /**
     * <p>コンストラクタです。指定されたキーを持つプロパティが初期設定されます。
     * 指定されたキーに対応する値は空文字列となります。</p>
     * 
     * @param key キー
     */
    public Properties(String key) {
        
        this(key, "");
    }
    
    /**
     * <p>コンストラクタです。指定されたキーおよび値を持つプロパティが初期設定されます。</p>
     * 
     * @param key キー
     * @param value 値
     */
	public Properties(final String key, final String value) {
		
		this.name = key;
		this.value = value;
		this.default_value = "";
		this.root = null;
		
		this.leaf.clear();
	}
	
	/**
	 * <p>コンストラクタです。指定されたデータでデフォルトが初期設定されます。</p>
     * 
	 * @param defaults デフォルトとなるキーと値を持つMapオブジェクト
	 */
	public Properties(Map<String, String> defaults) {
		
		this.name = "";
		this.value = "";
		this.default_value = "";
		this.root = null;
		
		this.leaf.clear();
		
		Iterator<Map.Entry<String, String>> it = defaults.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			setDefault(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * <p>コンストラクタです。指定されたデータでデフォルトが初期設定されます。</p>
     * 
	 * @param defaults デフォルト値を、キー・値の順に交互に並べたもの
	 */
	public Properties(final String[] defaults) {
		
		this.name = "";
		this.value = "";
		this.default_value = "";
		this.root = null;
		
		this.leaf.clear();
		
		setDefaults(defaults);
	}

	/**
	 * <p>コピーコンストラクタです。コピー元となるPropertiesオブジェクトと同内容を持つ
     * 別のPropertiesオブジェクトを新たに作成します。</p>
     * 
	 * @param prop コピー元となるPropertiesオブジェクト
	 */
	public Properties(final Properties prop) {
		
		this.name = prop.name;
		this.value = prop.value;
		this.default_value = prop.default_value;
		this.root = null;
		
		Vector<String> keys = prop.propertyNames();
		for (int i = 0; i < keys.size(); i++) {
			Properties node;
			if ((node = prop.getNode(keys.get(i))) != null) {
				setDefault(keys.get(i), node.default_value);
				setProperty(keys.get(i), node.value);
			}
		}
	}

	/**
	 * <p>指定されたPropertiesオブジェクトの内容を、当該Propertiesオブジェクトに設定します。</p>
     * 
	 * @param prop コピー元となるPropertiesオブジェクト
	 */
	public void substitute(final Properties prop) {
		
		clear();
        this.name = prop.name;
        this.value = prop.value;
        this.default_value = prop.default_value;

        Vector<String> keys = prop.propertyNames();
        for (int i = 0; i < keys.size(); i++) {
            Properties node;
            if ((node = prop.getNode(keys.get(i))) != null) {
                setDefault(keys.get(i), node.default_value);
                setProperty(keys.get(i), node.value);
            }
        }
	}
	
	/**
	 * <p>削除処理を行います。当該Propertiesオブジェクトの内容をクリアして、
     * 親ノードから切り離します。また、すべての子ノードについても削除処理を行います。</p>
	 */
	public void destruct() {
		
	    // Delete children
		clear();
		
		// delete myself from parent
		if (this.root != null) {
			this.root.removeNode(this.name);
		}
	}
	
	protected void finalize() throws Throwable {
		try {
			destruct();
            
		} finally {
			super.finalize();
		}
	}
	
    /**
     * <p>当該Propertiesオブジェクトのキーを取得します。</p>
     * 
     * @return キー
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>当該Propertiesオブジェクトの値を取得します。</p>
     * 
     * @return 値
     */
    public String getValue() {
        return this.value;
    }

    /**
     * <p>当該Propertiesオブジェクトのデフォルト値を取得します。</p>
     * 
     * @return デフォルト値
     */
    public String getDefaultValue() {
        return this.default_value;
    }

    /**
     * <p>当該Propertiesオブジェクトの子ノード群を取得します。</p>
     * 
     * @return 子ノードPropertiesオブジェクトリスト
     */
    public Vector<Properties> getLeaf() {
        return this.leaf;
    }

    /**
     * <p>当該Propertiesオブジェクトの親ノードを取得します。</p>
     * 
     * @return 親ノードPropertiesオブジェクト
     */
    public Properties getRoot() {
        return this.root;
    }
    
    /**
     * <p>指定されたキーに対応する値を取得します。</p>
     * 
     * @param key キー
     * @return 指定されたキーに対応する値を返します。<br />
     * 指定されたキーが存在するが値が設定されていない場合は、デフォルト値を返します。<br />
     * また、指定されたキーが存在しない場合は空文字列を返します。
     */
    public String getProperty(final String key) {
        
        Vector<String> keys = new Vector<String>();
        split(key, '.', keys);
        Properties node;
        if ((node = _getNode(keys, 0, this)) != null) {
            return (node.value.length() > 0) ? node.value : node.default_value;
        }
        
        return this.EMPTY;
    }
    
    /**
     * 指定されたキーに対応する値を取得します。ただし、指定されたキーが存在しない場合や、
     * キーに対応する値が空文字列である場合は、指定された代替値が取得されます。
     * 
     * @param key キー
     * @param alternative 代替値
     * @return 指定されたキーに対応する値を返します。<br />
     * 指定されたキーが存在しない場合や、キーに対応する値が空文字列である場合は、代替値を返します。
     */
    public String getProperty(final String key, final String alternative) {
        
        String value = getProperty(key);
        
        return (value.length() == 0) ? alternative : value;
    }
    
    /**
     * <p>指定されたキーに対応するデフォルト値を取得します。</p>
     * 
     * @param key キー
     * @return 指定されたキーが存在する場合は、それに対応するデフォルト値を返します。<br />
     * 指定されたキーが存在しない場合は、空文字列を返します。
     */
    public String getDefault(final String key) {
        
        Vector<String> keys = new Vector<String>();
        split(key, '.', keys);
        Properties node;
        if ((node = _getNode(keys, 0, this)) != null) {
            return node.default_value;
        }
        
        return this.EMPTY;
    }
    
    /**
     * <p>指定されたキーに対応する値を登録します。</p>
     * 
     * @param key キー
     * @param value キーに対応する値
     * @return 以前の設定値を返します。以前の設定値が存在しない場合は、デフォルト値を返します。
     */
    public String setProperty(final String key, final String value) {
        
        Vector<String> keys = new Vector<String>();
        split(key, '.', keys);
        
        Properties curr = this;
        for (int i = 0; i < keys.size(); i++) {
            Properties next = curr.hasKey(keys.get(i));
            if (next == null) {
                next = new Properties(keys.get(i));
                next.root = curr;
                curr.leaf.add(next);
            }
            curr = next;
        }

        String oldValue = (curr.value.length() > 0) ? curr.value : curr.default_value;
        curr.value = value;
        
        return oldValue;
    }
    
    /**
     * <p>指定されたキーに対応するデフォルト値を設定します。</p>
     * 
     * @param key キー
     * @param defaultValue キーに対応する新たなデフォルト値
     */
    public String setDefault(final String key, final String defaultValue) {
        
        Vector<String> keys = new Vector<String>();
        split(key, '.', keys);
        
        Properties curr = this;
        for (int i = 0; i < keys.size(); i++) {
            Properties next = curr.hasKey(keys.get(i));
            if (next == null) {
                next = new Properties(keys.get(i));
                next.root = curr;
                curr.leaf.add(next);
            }
            curr = next;
        }
        curr.default_value = defaultValue;
        
        return defaultValue;
    }
    
    /**
     * <p>指定されたキーに対応するデフォルト値を設定します。</p>
     * 
     * @param defaults デフォルト値を、キー・値の順に交互に並べたもの
     */
	public void setDefaults(final String[] defaults) {
		
		for (int i = 0; i + 1 < defaults.length; i += 2) {
			String key = defaults[i];
			String value = defaults[i + 1];
			
			key = key.trim();
			value = value.trim();
			
			setDefault(key, value);
		}
	}

    /**
     * <p>指定された出力ストリームに、当該Propertiesオブジェクトの内容を出力します。</p>
     * 
     * @param out 出力先ストリーム
     */
    public void list(OutputStream out) {
        _store(out, "", this);
    }
    
    /**
     * <p>指定されたリーダーから、キーと値が対となったプロパティセットを読み込みます。</p>
     * 
     * @param reader 読み込み元となるリーダー。
     */
    public void load(BufferedReader reader) throws IOException {
        
        String pline = ""; 
        
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            tmp = tmp.trim();
            
            // Skip comments or empty lines
            if (tmp.length() == 0 || tmp.charAt(0) == '#' || tmp.charAt(0) == '!') continue;
            
            // line-end '\' continues entry
            if (tmp.charAt(tmp.length() - 1) == '\\' && !StringUtil.isEscaped(tmp, tmp.length() - 1))
            {
                tmp = tmp.substring(0, tmp.length() - 1);
                pline += tmp;
                continue;
            }
            pline += tmp;
            
            // Skip empty line (made of only ' ' or '\t')
            if (pline.length() == 0) continue;
    
            Pair<String, String> pair = splitKeyValue(pline);
            String key = pair.getKey();
            String value = pair.getValue();
            
            key = StringUtil.unescape(key);
            key = key.trim();

            value = StringUtil.unescape(value);
            value = value.trim();
    
            setProperty(key, value);
            pline = "";
        }
    }
    
    /**
     * <p>当該Propertiesオブジェクトの内容を、指定されたストリームに出力します。</p>
     * 
     * @param out 出力先ストリーム
     * @param header 出力内容の先頭に付加されるヘッダコメント文字列
     */
    public void save(OutputStream out, final String header) {
        store(out, header);
    }
    
    /**
     * <p>当該Propertiesオブジェクトの内容を、指定されたストリームに出力します。</p>
     * 
     * @param out 出力先ストリーム
     * @param header 出力内容の先頭に付加されるヘッダコメント文字列
     */
    public void store(OutputStream out, final String header) {

        PrintWriter writer = new PrintWriter(out);
        writer.write("# ");
        writer.write(header);
        writer.println();
        writer.flush();
        
        _store(out, "", this);
    }
    
    /**
     * <p>当該Propertiesオブジェクトおよび子ノード内に存在するキーのリストを取得します。</p>
     * 
     * @return キーのリスト
     */
    public final Vector<String> propertyNames() {
        
        Vector<String> names = new Vector<String>();
        for (int i = 0; i < leaf.size(); i++) {
            _propertyNames(names, leaf.get(i).name, leaf.get(i));
        }
        
        return names;
    }
    
    /**
     * <p>当該Propertiesオブジェクトおよび子ノード内に存在するプロパティの数を取得します。
     * 
     * @return プロパティ数
     */
    public final int size() {
        return propertyNames().size();
    }
    
    /**
     * <p>指定されたキーに対応する値を直接保持しているPropertiesオブジェクトを取得します。</p>
     * 
     * @param key キー
     * @return 指定されたキーに対応する値を直接保持しているPropertiesオブジェクト
     */
    public final Properties getNode(final String key) {
        
        Vector<String> keys = new Vector<String>();
        split(key, '.', keys);
        
        return _getNode(keys, 0, this);
    }
    
    /**
     * <p>指定されたキーのPropertiesオブジェクトを、当該Propertiesオブジェクト内に
     * 子ノードとして作成します。</p>
     * 
     * @param key キー
     * @return 指定されたキーがすでに存在していた場合はfalseを、さもなくばtrueを返します。
     */
    public boolean createNode(final String key) {
        
        Properties p = getNode(key);
        if (p != null) {
            return false;
        }
        
        this.setProperty(key, "");
        
        return true;
    }
    
    /**
     * <p>指定されたキーの子ノードPropertiesオブジェクトを、
     * 当該Propertiesオブジェクトから切り離します。</p>
     * 
     * @param name 切り離したい子ノードのキー
     * @return 切り離された子ノードPropertiesオブジェクトを返します。<br />
     * 指定されたキーに対応する子ノードが存在しない場合は、nullを返します。
     */
    public Properties removeNode(String name) {
        
        Properties prop;
        for (int i = 0; i < this.leaf.size(); i++) {
            if (this.leaf.get(i).name.equals(name)) {
                prop = this.leaf.get(i);
                this.leaf.remove(i);
                return prop;
            }
        }
        
        return null;
    }
    
    /**
     * <p>当該Propertiesオブジェクトの直接の子ノード（つまり、孫ノード以下は含まない）の中に、
     * 指定されたキーを持つものが存在するかどうか調べます。存在する場合には、
     * そのPropertiesオブジェクトを取得します。</p>
     * 
     * @param key キー
     * @return 指定されたキーに対応するPropertiesオブジェクトを返します。<br />
     * 指定されたキーに対応するPropertiesオブジェクトが存在しない場合は、nullを返します。
     */
    public final Properties hasKey(final String key) {
        
        for (int i = 0; i < this.leaf.size(); ++i) {
            if (this.leaf.get(i).name.equals(key)) {
                return this.leaf.get(i);
            }
        }
        
        return null;
    }
    
    /**
     * <p>子ノードを全て削除します。</p>
     */
    public void clear() {
        
        while (this.leaf.size() > 0) {
            Properties tail = this.leaf.get(this.leaf.size() - 1);
            if (tail != null) {
                tail.destruct();
            }
        }
    }
    
    /**
     * <p>指定されたPropertiesオブジェクト内のプロパティセットを、当該Properties
     * オブジェクト内にマージします。ただし、デフォルト値はマージされません。</p>
     * 
     * @param prop マージ元となるPropertiesオブジェクト
     */
    public void merge(final Properties prop) {
        
        for (Iterator<String> it = prop.propertyNames().iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = prop.getProperty(key);
            this.setProperty(key, value);
        }
    }
    
    /**
     * <p>指定された文字列を、所定のデリミタによってキーと値のペアに分離します。</p>
     * 
     * @param str 分離対象となる文字列。通常は、キーと値がデリミタで区切られている文字列。
     * @return キーと値を格納するPairオブジェクト。
     */
    protected Pair<String, String> splitKeyValue(final String str) {
        
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) == ':' || str.charAt(i) == '=')
                    && !StringUtil.isEscaped(str, i)) {
                return new Pair<String, String>(str.substring(0, i).trim(), str.substring(i + 1).trim());
            }
        }
        
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) == ' ') && StringUtil.isEscaped(str, i)) {
                return new Pair<String, String>(str.substring(0, i).trim(), str.substring(i + 1).trim());
            }
        }

        return new Pair<String, String>(str, "");
    }
    
    /**
     * <p>指定された文字列を指定したデリミタで分離した結果を取得します。</p>
     * 
     * @param str 分離対象となる文字列
     * @param delim デリミタ
     * @return 分離された文字列のリスト
     */
    protected boolean split(final String str, final char delim, Vector<String> values) {
        
        if (str.length() == 0) {
            return false;
        }
        
        int begin_idx = 0;
        int end_idx = 0;
        
        while (end_idx < str.length()) {
            if ((str.charAt(end_idx) == delim) && !StringUtil.isEscaped(str, end_idx)) {
                values.add(str.substring(begin_idx, end_idx));
                begin_idx = end_idx + 1;
            }
            end_idx++;
        }
        values.add(str.substring(begin_idx));
        
        return true;
    }
    
    protected String indent(int index) {
        StringBuffer spaces = new StringBuffer();
        for (int i = 0; i < index - 1; i++) {
            spaces.append("  ");
        }
        
        return spaces.toString();
    }
    
    protected Properties _getNode(Vector<String> keys, int index, final Properties curr) {
        Properties next = curr.hasKey(keys.get(index));
        
        if (next == null) {
            return null;
        }
        
        if (index < keys.size() - 1) {
            return next._getNode(keys, ++index, next);
        }
        
        return next;
    }
    
    protected void _propertyNames(Vector<String> names, String curr_name, final Properties curr) {
        
        if (!curr.leaf.isEmpty()) {
            for (int i = 0; i < curr.leaf.size(); i++) {
                String next_name = curr_name + '.' + curr.leaf.get(i).name;
                _propertyNames(names, next_name, curr.leaf.get(i));
            }
        } else {
            names.add(curr_name);
        }
    }
    
    protected void _store(OutputStream out, String curr_name, Properties curr) {
        
        if (!curr.leaf.isEmpty()) {
            for (int i = 0; i < curr.leaf.size(); i++) {
                String next_name;
                if (curr_name.length() == 0) {
                    next_name = curr.leaf.get(i).name;
                } else {
                    next_name = curr_name + '.' + curr.leaf.get(i).name;
                }
                _store(out, next_name, curr.leaf.get(i));
            }
        }
        
        if (curr.root != null) {
            if ((curr.value != null) && (curr.value.length() > 0)) {
                PrintWriter writer = new PrintWriter(out);
                writer.write(curr_name);
                writer.write(" = ");
                writer.write(curr.value);
                writer.println();
                writer.flush();
            }
        }
    }
    
    protected OutputStream _dump(OutputStream out, final Properties curr, int index) {
        
        PrintWriter writer = new PrintWriter(out);
        
        if (index != 0) {
            writer.write(indent(index));
            writer.write("- ");
            writer.write(curr.name);
        }
        
        if (curr.leaf.isEmpty()) {
            if (curr.value.length() == 0) {
                writer.write(": ");
                writer.write(curr.default_value);
                writer.println();
            } else {
                writer.write(": ");
                writer.write(curr.value);
                writer.println();
            }
            return out;
        }

        if (index != 0) {
            writer.println();
        }
        
        for (int i = 0; i < curr.leaf.size(); i++) {
            _dump(out, curr.leaf.get(i), index + 1);
        }
        
        return out;
    }
}
