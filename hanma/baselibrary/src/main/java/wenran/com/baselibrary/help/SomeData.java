package wenran.com.baselibrary.help;

import java.io.Serializable;

/**
 * Created by Crowhine on 2019/3/19
 *
 * @author Crowhine
 */
public class SomeData<T extends Serializable> implements Serializable{
    private static final long serialVersionUID = -2505579992117448932L;
    String tag;
    T t;

    public SomeData() {
    }

    public SomeData(String tag, T t) {
        this.tag = tag;
        this.t = t;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
