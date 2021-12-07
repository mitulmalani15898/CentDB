package centdb.DBQuery.constants;

public final class Constants {
    public static final String selectFieldsQueryCheck =  "^(?i)(SELECT\\s[a-zA-Z\\d_]+(,\\s[a-zA-Z\\d_]+)*\\sFROM\\s[a-zA-Z\\\\d]+(\\swhere\\s[a-zA-Z\\d_]+\\s[<>]?[=]\\s[a-zA-Z\\d\\.]+)?;)$";
    public static final String selectAllQueryCheck = "^(?i)(SELECT\\s\\*\\sFROM\\s[a-zA-Z\\d]+(\\swhere\\s[a-zA-Z\\d_]+\\s[<>]?[=]\\s[a-zA-Z\\d\\.]+)?;)$";

}
