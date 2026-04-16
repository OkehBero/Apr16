public class test {
    public static void main(String[] args) {
        String nama = "Budi";
        String pekerjaan = "Programmer";

        String template = """
            {
                "nama": "%s",
                "pekerjaan": "%s",
                "status": "Aktif"
            }
            """.formatted(nama, pekerjaan);

        System.out.println(template);
    }
}
