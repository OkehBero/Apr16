interface DataImportable {
    public void importFromCsv(String path, AdminIOCsvMode mode) throws DataFileException;
}