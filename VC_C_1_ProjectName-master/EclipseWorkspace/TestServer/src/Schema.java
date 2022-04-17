

/**
 *
 * @author ohcsu_000
 */
public class Schema 
{
    //INSTANCE DATA/////////////////////////////////////////////////////////////
    private String schemaString = "";
    private String dataTypes[];
    
    public Schema(int attributes)
    {
        dataTypes = new String[attributes];
    }
    
    public Schema()//error
    {
        schemaString = "fail op code mismatch";
    }
    
    public String getSchemaString()
    {
        return schemaString;
    }
    
    public void setSchemaString(String s)
    {
        schemaString = s;
    }
    
    public void addDataType(String data_type, int i)
    {
        dataTypes[i] = data_type;
    }
}
