import java.util.Arrays;
import java.util.List;

/**
 * Ana uygulama sınıfı — Bildirim Sistemini test eder.
 * 
 * Faz 2 sonrası: Decorator ve Facade örüntüleri aktif.
 * - Decorator: Loglama, tekrar deneme, şifreleme katmanları
 * - Facade: Basitleştirilmiş API (emailGonder, smsGonder, pushGonder)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("   BILDIRIM SISTEMI - Faz 2 Demo");
        System.out.println("   Decorator + Facade Patterns");
        System.out.println("=============================================\n");

        // ===== BOLUM 1: Decorator Pattern Demo =====
        System.out.println("############################################");
        System.out.println("# BOLUM 1: Decorator Pattern               #");
        System.out.println("############################################\n");

        // --- Test 1: Sade bildirim (decorator yok) ---
        System.out.println("--- Test 1: Sade Email (decorator yok) ---");
        Bildirim email = BildirimFactory.bildirimOlustur(BildirimTipi.EMAIL);
        email.gonder("test@example.com", "Basit Test", "Decorator olmadan gonderim", 1);

        // --- Test 2: Loglama Decorator ---
        System.out.println("\n--- Test 2: Loglama Decorator ---");
        Bildirim logluEmail = new LoglamaDecorator(
            BildirimFactory.bildirimOlustur(BildirimTipi.EMAIL)
        );
        logluEmail.gonder("test@example.com", "Loglu Test", "Bu gonderim loglanir", 2);

        // --- Test 3: Sifreleme Decorator ---
        System.out.println("\n--- Test 3: Sifreleme Decorator ---");
        Bildirim sifreliSms = new SifrelemeDecorator(
            BildirimFactory.bildirimOlustur(BildirimTipi.SMS)
        );
        sifreliSms.gonder("+905551234567", "Guvenli Mesaj", "Bu mesaj sifrelenir", 1);

        // --- Test 4: Zincirlenmis Decorator (Log + Sifreleme) ---
        System.out.println("\n--- Test 4: Zincirlenmis Decorator (Log + Sifreleme) ---");
        Bildirim tamDonatimli = new LoglamaDecorator(
            new SifrelemeDecorator(
                BildirimFactory.bildirimOlustur(BildirimTipi.PUSH)
            )
        );
        tamDonatimli.gonder("device-token-abc123xyz789def456", "Tam Donatimli", "Log + Sifreleme birlikte", 3);

        // --- Test 5: Tekrar Deneme Decorator ---
        System.out.println("\n--- Test 5: Tekrar Deneme Decorator (basarisiz alici) ---");
        Bildirim retryEmail = new TekrarDenemeDecorator(
            BildirimFactory.bildirimOlustur(BildirimTipi.EMAIL),
            3,  // max 3 deneme
            500  // 500ms bekleme
        );
        retryEmail.gonder("gecersiz-adres", "Retry Test", "Bu basarisiz olacak", 1);

        // ===== BOLUM 2: Facade Pattern Demo =====
        System.out.println("\n\n############################################");
        System.out.println("# BOLUM 2: Facade Pattern                  #");
        System.out.println("############################################\n");

        // Facade olustur (loglama acik, sifreleme kapali)
        BildirimFacade facade = new BildirimFacade(true, false);

        // --- Test 6: Facade ile basit email ---
        System.out.println("--- Test 6: Facade ile Email ---");
        facade.emailGonder("kullanici@example.com", "Hos Geldiniz", "Kaydiniz tamamlandi!");

        // --- Test 7: Facade ile SMS ---
        System.out.println("\n--- Test 7: Facade ile SMS ---");
        facade.smsGonder("+905559876543", "Dogrulama", "Kodunuz: 654321");

        // --- Test 8: Facade ile Push ---
        System.out.println("\n--- Test 8: Facade ile Push ---");
        facade.pushGonder("device-token-xyz987abc654def321", "Yeni Bildirim", "Yeni bir mesajiniz var");

        // --- Test 9: Facade ile Acil Bildirim ---
        System.out.println("\n--- Test 9: Acil Bildirim (otomatik retry) ---");
        facade.acilBildirimGonder(BildirimTipi.EMAIL, "admin@example.com", "ALARM", "Sistem coktu!");

        // --- Test 10: Sifrelemeli Facade ---
        System.out.println("\n--- Test 10: Sifrelemeli Facade ---");
        BildirimFacade guvenliFacade = new BildirimFacade(true, true);
        guvenliFacade.smsGonder("+905551112233", "Banka", "Hesabiniza 1000 TL yattirildi");

        // ===== BOLUM 3: Singleton Dogrulama =====
        System.out.println("\n\n############################################");
        System.out.println("# BOLUM 3: Singleton Dogrulama             #");
        System.out.println("############################################\n");

        BildirimServisi s1 = BildirimServisi.getInstance();
        BildirimServisi s2 = BildirimServisi.getInstance();
        System.out.println("Ayni instance mi? " + (s1 == s2));

        // ===== BOLUM 4: Istatistikler =====
        System.out.println("\n\n############################################");
        System.out.println("# BOLUM 4: Istatistikler & Gecmis          #");
        System.out.println("############################################");

        BildirimServisi.getInstance().istatistikYazdir();

        System.out.println("\n=== BILDIRIM GECMISI ===");
        List<String> gecmis = BildirimServisi.getInstance().gecmisGetir(null);
        for (String kayit : gecmis) {
            System.out.println("  " + kayit);
        }

        System.out.println("\n=============================================");
        System.out.println("   Demo tamamlandi.");
        System.out.println("=============================================");
    }
}
