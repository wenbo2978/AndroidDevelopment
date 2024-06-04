package cwb.client.data;

/**
 * Created by PC_chen on 2019/3/9.
 */

public class HotKeyData {
    String key;
    String value;

    public HotKeyData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
