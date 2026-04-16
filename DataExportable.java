interface DataExportable {
    public void exportToCsv(String path, AdminIOCsvMode mode) throws DataFileException;
    public void exportToTxt(String path, AdminIOCsvMode mode) throws DataFileException;
}