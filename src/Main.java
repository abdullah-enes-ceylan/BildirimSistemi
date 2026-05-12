import java.util.Arrays;
import java.util.List;

/**
 * Ana uygulama sınıfı - Bildirim Sistemini test eder.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   BİLDİRİM SİSTEMİ - Faz 0 Demo");
        System.out.println("========================================\n");

        // BildirimServisi oluştur (Singleton değil, doğrudan new)
        BildirimServisi servis = new BildirimServisi();

        // --- E-posta Bildirimi ---
        System.out.println("\n--- Test 1: E-posta Bildirimi ---");
        servis.bildirimGonder(
            BildirimServisi.EMAIL,
            "kullanici@example.com",
            "Hoş Geldiniz",
            "Sisteme kayıt işleminiz tamamlandı.",
            1
        );

        // --- SMS Bildirimi ---
        System.out.println("\n--- Test 2: SMS Bildirimi ---");
        servis.bildirimGonder(
            BildirimServisi.SMS,
            "+905551234567",
            "Doğrulama Kodu",
            "Kodunuz: 123456",
            2
        );

        // --- Push Bildirimi ---
        System.out.println("\n--- Test 3: Push Bildirimi ---");
        servis.bildirimGonder(
            BildirimServisi.PUSH,
            "device-token-abc123xyz789def456",
            "Yeni Mesaj",
            "Bir arkadaşınız size mesaj gönderdi.",
            1
        );

        // --- Acil Bildirim (yüksek öncelikli) ---
        System.out.println("\n--- Test 4: Acil E-posta Bildirimi ---");
        servis.bildirimGonder(
            BildirimServisi.EMAIL,
            "admin@example.com",
            "Sistem Uyarısı",
            "Sunucu CPU kullanımı %95'i aştı!",
            5
        );

        // --- Hatalı bildirim testi ---
        System.out.println("\n--- Test 5: Geçersiz E-posta ---");
        servis.bildirimGonder(
            BildirimServisi.EMAIL,
            "gecersiz-adres",
            "Test",
            "Bu gitmemeli",
            1
        );

        // --- Bilinmeyen tip ---
        System.out.println("\n--- Test 6: Bilinmeyen Tip ---");
        servis.bildirimGonder(
            "TELEGRAM",
            "@kullanici",
            "Test",
            "Bu da gitmemeli",
            1
        );

        // --- Toplu bildirim ---
        System.out.println("\n--- Test 7: Toplu SMS Bildirimi ---");
        List<String> telefonlar = Arrays.asList(
            "+905551111111",
            "+905552222222",
            "+905553333333"
        );
        servis.topluBildirimGonder(
            BildirimServisi.SMS,
            telefonlar,
            "Kampanya",
            "Bu hafta tüm ürünlerde %20 indirim!",
            2
        );

        // --- İstatistikler ---
        servis.istatistikYazdir();

        // --- Geçmiş ---
        System.out.println("\n=== BİLDİRİM GEÇMİŞİ ===");
        List<String> gecmis = servis.gecmisGetir(null);
        for (String kayit : gecmis) {
            System.out.println("  " + kayit);
        }

        // --- Sadece e-posta geçmişi ---
        System.out.println("\n=== SADECE E-POSTA GEÇMİŞİ ===");
        List<String> emailGecmis = servis.gecmisGetir(BildirimServisi.EMAIL);
        for (String kayit : emailGecmis) {
            System.out.println("  " + kayit);
        }

        System.out.println("\n========================================");
        System.out.println("   Demo tamamlandı.");
        System.out.println("========================================");
    }
}
