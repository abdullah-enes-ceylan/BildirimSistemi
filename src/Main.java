import java.util.Arrays;
import java.util.List;

/**
 * Ana uygulama sınıfı — Bildirim Sistemini test eder.
 * 
 * Faz 3 (Final): Tüm tasarım örüntüleri aktif.
 * - Creational: Factory Method, Singleton
 * - Structural: Decorator, Facade
 * - Behavioral: Observer, Strategy
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("   BILDIRIM SISTEMI - Final Demo");
        System.out.println("   6 Tasarim Oruntsu Aktif");
        System.out.println("=============================================\n");

        // ===== Observer Kurulumu =====
        BildirimOlayYoneticisi olayYoneticisi = BildirimOlayYoneticisi.getInstance();
        IstatistikDinleyici istatistik = new IstatistikDinleyici();
        GecmisDinleyici gecmis = new GecmisDinleyici();
        olayYoneticisi.dinleyiciEkle(istatistik);
        olayYoneticisi.dinleyiciEkle(gecmis);

        BildirimServisi servis = BildirimServisi.getInstance();

        // ===== BOLUM 1: Factory Method + Observer =====
        System.out.println("\n############################################");
        System.out.println("# BOLUM 1: Factory + Observer              #");
        System.out.println("############################################\n");

        System.out.println("--- Test 1: Email Bildirimi ---");
        servis.bildirimGonder(BildirimTipi.EMAIL, "user@example.com", "Hosgeldiniz", "Kaydiniz tamamlandi", 1);

        System.out.println("\n--- Test 2: SMS Bildirimi ---");
        servis.bildirimGonder(BildirimTipi.SMS, "+905551234567", "Dogrulama", "Kodunuz: 123456", 2);

        System.out.println("\n--- Test 3: Push Bildirimi ---");
        servis.bildirimGonder(BildirimTipi.PUSH, "device-token-abc123xyz789def456", "Yeni Mesaj", "Mesajiniz var", 1);

        // ===== BOLUM 2: Strategy Pattern =====
        System.out.println("\n\n############################################");
        System.out.println("# BOLUM 2: Strategy Pattern                #");
        System.out.println("############################################\n");

        // Oncelik filtresi: sadece oncelik >= 3 gonderilebilir
        System.out.println("--- Test 4: Oncelik Filtresi (min=3) ---");
        servis.setFiltrelemeStratejisi(new OncelikFiltrelemeStratejisi(3));
        servis.bildirimGonder(BildirimTipi.EMAIL, "user@example.com", "Dusuk Oncelik", "Bu engellenmeli", 1);
        servis.bildirimGonder(BildirimTipi.EMAIL, "user@example.com", "Yuksek Oncelik", "Bu gitmeli", 4);

        // Sessiz mod: Push engellensin
        System.out.println("\n--- Test 5: Sessiz Mod (Push engelli) ---");
        SessizModStratejisi sessizMod = new SessizModStratejisi();
        sessizMod.tipEngelle("PUSH");
        sessizMod.tipEngelle("SMS");
        servis.setFiltrelemeStratejisi(sessizMod);

        servis.bildirimGonder(BildirimTipi.EMAIL, "user@example.com", "Email OK", "Email gider", 1);
        servis.bildirimGonder(BildirimTipi.PUSH, "device-token-abc123xyz789def456", "Push Engel", "Bu engellenir", 2);
        servis.bildirimGonder(BildirimTipi.SMS, "+905551234567", "SMS Engel", "Bu da engellenir", 1);

        // Acil bildirimler sessiz modda bile gecer
        System.out.println("\n--- Test 6: Acil Bildirim (sessiz modda bile gecer) ---");
        servis.bildirimGonder(BildirimTipi.PUSH, "device-token-abc123xyz789def456", "ACIL ALARM", "Sunucu coktu!", 5);

        // Stratejiyi sifirla
        servis.setFiltrelemeStratejisi(new HepsiniGonderStratejisi());

        // ===== BOLUM 3: Decorator Zincirleme =====
        System.out.println("\n\n############################################");
        System.out.println("# BOLUM 3: Decorator Zincirleme            #");
        System.out.println("############################################\n");

        System.out.println("--- Test 7: Log + Sifreleme Decorator ---");
        Bildirim tamDonatimli = new LoglamaDecorator(
            new SifrelemeDecorator(
                BildirimFactory.bildirimOlustur(BildirimTipi.EMAIL)
            )
        );
        tamDonatimli.gonder("admin@example.com", "Gizli Rapor", "Cok gizli bilgi", 3);

        // ===== BOLUM 4: Facade =====
        System.out.println("\n\n############################################");
        System.out.println("# BOLUM 4: Facade                          #");
        System.out.println("############################################\n");

        BildirimFacade facade = new BildirimFacade(true, false);

        System.out.println("--- Test 8: Facade ile Basit Gonderim ---");
        facade.emailGonder("basit@example.com", "Facade Test", "Tek satirla gonderim!");
        facade.smsGonder("+905559999999", "Facade SMS", "Basit API");

        // ===== BOLUM 5: OCP Demonstrasyonu =====
        System.out.println("\n\n############################################");
        System.out.println("# BOLUM 5: OCP Demonstrasyonu              #");
        System.out.println("############################################\n");

        System.out.println("OCP (Open/Closed Principle) kaniti:");
        System.out.println("  - Yeni bildirim tipi: sadece yeni sinif + enum degeri");
        System.out.println("  - Yeni decorator: sadece yeni sinif (LoglamaDecorator gibi)");
        System.out.println("  - Yeni observer: sadece BildirimOlayDinleyici implement et");
        System.out.println("  - Yeni strateji: sadece FiltrelemeStratejisi implement et");
        System.out.println("  -> Hicbir mevcut sinif degismez!\n");

        // ===== BOLUM 6: Observer Sonuclari =====
        System.out.println("\n############################################");
        System.out.println("# BOLUM 6: Observer Sonuclari              #");
        System.out.println("############################################");

        istatistik.istatistikleriYazdir();
        gecmis.gecmisYazdir();

        System.out.println("\n=============================================");
        System.out.println("   Demo tamamlandi.");
        System.out.println("=============================================");
    }
}
