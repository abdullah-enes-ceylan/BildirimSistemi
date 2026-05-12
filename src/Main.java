import java.util.Arrays;
import java.util.List;

/**
 * Ana uygulama sınıfı — Bildirim Sistemini test eder.
 * 
 * Faz 1 sonrası: Factory Method ve Singleton örüntüleri aktif.
 * - BildirimServisi.getInstance() ile tek instance kullanılıyor
 * - BildirimTipi enum ile type-safe bildirim tipi seçimi
 * - BildirimFactory ile nesne yaratma merkezi ve esnek
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   BILDIRIM SISTEMI - Faz 1 Demo");
        System.out.println("   Factory Method + Singleton");
        System.out.println("========================================\n");

        // Singleton ile tek instance
        BildirimServisi servis = BildirimServisi.getInstance();

        // --- E-posta Bildirimi ---
        System.out.println("\n--- Test 1: E-posta Bildirimi ---");
        servis.bildirimGonder(
            BildirimTipi.EMAIL,
            "kullanici@example.com",
            "Hos Geldiniz",
            "Sisteme kayit isleminiz tamamlandi.",
            1
        );

        // --- SMS Bildirimi ---
        System.out.println("\n--- Test 2: SMS Bildirimi ---");
        servis.bildirimGonder(
            BildirimTipi.SMS,
            "+905551234567",
            "Dogrulama Kodu",
            "Kodunuz: 123456",
            2
        );

        // --- Push Bildirimi ---
        System.out.println("\n--- Test 3: Push Bildirimi ---");
        servis.bildirimGonder(
            BildirimTipi.PUSH,
            "device-token-abc123xyz789def456",
            "Yeni Mesaj",
            "Bir arkadasiniz size mesaj gonderdi.",
            1
        );

        // --- Acil Bildirim (yüksek öncelikli) ---
        System.out.println("\n--- Test 4: Acil E-posta Bildirimi ---");
        servis.bildirimGonder(
            BildirimTipi.EMAIL,
            "admin@example.com",
            "Sistem Uyarisi",
            "Sunucu CPU kullanimi %95'i asti!",
            5
        );

        // --- Hatalı bildirim testi ---
        System.out.println("\n--- Test 5: Gecersiz E-posta ---");
        servis.bildirimGonder(
            BildirimTipi.EMAIL,
            "gecersiz-adres",
            "Test",
            "Bu gitmemeli",
            1
        );

        // --- Factory Method doğrudan kullanımı ---
        System.out.println("\n--- Test 6: Factory Method Dogrudan Kullanimi ---");
        Bildirim sms = BildirimFactory.bildirimOlustur(BildirimTipi.SMS);
        System.out.println("Olusturulan bildirim tipi: " + sms.getTipAdi());
        System.out.println("Alici dogrulama (+90555): " + sms.aliciDogrula("+905551234567"));
        System.out.println("Alici dogrulama (12345): " + sms.aliciDogrula("12345"));

        // --- Toplu bildirim ---
        System.out.println("\n--- Test 7: Toplu SMS Bildirimi ---");
        List<String> telefonlar = Arrays.asList(
            "+905551111111",
            "+905552222222",
            "+905553333333"
        );
        servis.topluBildirimGonder(
            BildirimTipi.SMS,
            telefonlar,
            "Kampanya",
            "Bu hafta tum urunlerde %20 indirim!",
            2
        );

        // --- Singleton doğrulaması ---
        System.out.println("\n--- Test 8: Singleton Dogrulamasi ---");
        BildirimServisi servis2 = BildirimServisi.getInstance();
        System.out.println("Ayni instance mi? " + (servis == servis2)); // true olmali

        // --- İstatistikler ---
        servis.istatistikYazdir();

        // --- Geçmiş ---
        System.out.println("\n=== BILDIRIM GECMISI ===");
        List<String> gecmis = servis.gecmisGetir(null);
        for (String kayit : gecmis) {
            System.out.println("  " + kayit);
        }

        System.out.println("\n========================================");
        System.out.println("   Demo tamamlandi.");
        System.out.println("========================================");
    }
}
