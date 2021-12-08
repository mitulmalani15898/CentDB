package centdb.dbquery.constants;

public final class Constants {
    public static final String selectFieldsQueryCheck =  "^(?i)(SELECT\\s[a-zA-Z\\d_]+(,\\s[a-zA-Z\\d_]+)*\\sFROM\\s[a-zA-Z\\\\d]+(\\swhere\\s[a-zA-Z\\d_]+\\s[<>]?[=]\\s[a-zA-Z\\d\\.]+)?;)$";
    public static final String selectAllQueryCheck = "^(?i)(SELECT\\s\\*\\sFROM\\s[a-zA-Z\\d]+(\\swhere\\s[a-zA-Z\\d_]+\\s[<>]?[=]\\s[a-zA-Z\\d\\.]+)?;)$";
    public static final String updateQuery = "^(?i)(UPDATE\\s[a-zA-Z\\d]+\\s(set)\\s[a-zA-Z\\d_]+\\s[=]\\s[a-zA-Z\\d_]+(,\\s[a-zA-Z\\d_]+\\s[=]\\s[a-zA-Z\\d_]+)*\\s(where)\\s[a-zA-Z\\d_]+\\s[=]\\s[a-zA-Z\\d_]+;)";

}
