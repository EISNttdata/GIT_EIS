package soa.model.binding.file;


public class FileMessage {
  String fileName;
  String pollingInterval;
  String use;
  String part;
  String removeEOL;
  String maxBytesPerRecord;
  String multipleRecordsPerFile;
  String archive;
  String archiveDirIsRelative;
  String deleteFileOnRead;
  
  public String getDeleteFileOnRead()
  {
    return this.deleteFileOnRead;
  }
  




  public void setDeleteFileOnRead(String deleteFileOnRead)
  {
    this.deleteFileOnRead = deleteFileOnRead;
  }
  
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public String getArchive() {
    return this.archive;
  }
  
  public void setArchive(String archive) {
    this.archive = archive;
  }
  
  public String getArchiveDirIsRelative() {
    return this.archiveDirIsRelative;
  }
  
  public void setArchiveDirIsRelative(String archiveDirIsRelative) {
    this.archiveDirIsRelative = archiveDirIsRelative;
  }
  
  public String getMaxBytesPerRecord() {
    return this.maxBytesPerRecord;
  }
  
  public void setMaxBytesPerRecord(String maxBytesPerRecord) {
    this.maxBytesPerRecord = maxBytesPerRecord;
  }
  
  public String getMultipleRecordsPerFile() {
    return this.multipleRecordsPerFile;
  }
  
  public void setMultipleRecordsPerFile(String multipleRecordsPerFile) {
    this.multipleRecordsPerFile = multipleRecordsPerFile;
  }
  
  public String getPart() {
    return this.part;
  }
  
  public void setPart(String part) {
    this.part = part;
  }
  
  public String getPollingInterval() {
    return this.pollingInterval;
  }
  
  public void setPollingInterval(String pollingInterval) {
    this.pollingInterval = pollingInterval;
  }
  
  public String getRemoveEOL() {
    return this.removeEOL;
  }
  
  public void setRemoveEOL(String removeEOL) {
    this.removeEOL = removeEOL;
  }
  
  public String getUse() {
    return this.use;
  }
  
  public void setUse(String use) {
    this.use = use;
  }
}
