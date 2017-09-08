package soa.model.adapter;

/**
 * Created by Manoj_Mehta on 4/13/2017.
 */
public class AdapterOperation {

    public static final String READ_FILE="Read";
    public static final String FTP_PUT="Put";
    public static final String SYNCHRONOUS_READFILE="Synchronous Read";
    public static final String lIST_FILE="FileListing";
    public static final String WRITE_FILE="Write";
    public static final String CHUNCKED_READ="Chuncked Read";

    /**
     * @param adapter
     * @return
     */
    public static String getOperationType(String adapter)
    {
        if(adapter.equalsIgnoreCase("FilePoller"))
        {
            return READ_FILE;
        }
        else if(adapter.equalsIgnoreCase("ListFiles"))
        {
          return lIST_FILE;
        }
        else if(adapter.equalsIgnoreCase("FTPPut"))
        {
            return FTP_PUT;
        }
        return "NA#";
    }

}
