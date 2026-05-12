import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bildirim Servisi - Tüm bildirim işlemleri tek bir sınıfta.
 * E-posta, SMS ve Push bildirimleri bu sınıf üzerinden gönderilir.
 * 
 * NOT: Bu kod bilinçli olarak tasarım örüntüsü kullanılmadan,
 * kötü pratiklerle yazılmıştır (Faz 0 - Başlangıç Kodu).
 */
public class BildirimServisi {

    // Bildirim tipleri sabit kodlanmış
    public static final String EMAIL = "EMAIL";
    public static final String SMS = "SMS";
    public static final String PUSH = "PUSH";

    // Tüm bildirim geçmişi burada tutuluyor (tek liste)
    private List<String> bildirimGecmisi = new ArrayList<>();

    // E-posta ayarları
    private String smtpSunucu = "smtp.example.com";
    private int smtpPort = 587;
    private String smtpKullaniciAdi = "admin@example.com";
    private String smtpSifre = "sifre123";

    // SMS ayarları
    private String smsApiAnahtari = "api-key-12345";
    private String smsGonderenNumara = "+905551234567";
    private String smsApiUrl = "https://sms-api.example.com/send";

    // Push bildirim ayarları
    private String pushApiAnahtari = "push-api-key-xyz";
    private String pushSunucuUrl = "https://push.example.com/notify";
    private String pushUygulamaId = "com.example.bildirim";

    // Singleton benzeri ama düzgün uygulanmamış
    private static BildirimServisi instance;

    public BildirimServisi() {
        // Constructor public - herkes yeni instance oluşturabilir
        // Singleton değil ama tek instance olması gerekiyor aslında
    }

    public static BildirimServisi getInstance() {
        if (instance == null) {
            instance = new BildirimServisi();
        }
        return instance;
    }

    /**
     * Bildirim gönderme - tüm tipler tek metotta, if-else ile ayrılıyor
     */
    public boolean bildirimGonder(String tip, String alici, String baslik, String mesaj, int oncelik) {
        System.out.println("=== Bildirim Gönderiliyor ===");
        System.out.println("Tip: " + tip);
        System.out.println("Alıcı: " + alici);

        boolean sonuc = false;

        // Dev if-else zinciri - her bildirim tipi burada
        if (tip.equals(EMAIL)) {
            // E-posta gönderme simülasyonu
            System.out.println("E-posta gönderiliyor...");
            System.out.println("SMTP Sunucu: " + smtpSunucu + ":" + smtpPort);
            System.out.println("Gönderen: " + smtpKullaniciAdi);

            // E-posta formatı kontrol
            if (alici == null || !alici.contains("@")) {
                System.out.println("HATA: Geçersiz e-posta adresi!");
                return false;
            }

            // E-posta gövdesi oluştur
            String emailGovde = "<html><body>";
            emailGovde += "<h1>" + baslik + "</h1>";
            emailGovde += "<p>" + mesaj + "</p>";
            if (oncelik > 3) {
                emailGovde += "<p style='color:red'>⚠ ACIL BİLDİRİM</p>";
            }
            emailGovde += "</body></html>";

            System.out.println("E-posta gövdesi: " + emailGovde);
            System.out.println("E-posta başarıyla gönderildi: " + alici);
            sonuc = true;

        } else if (tip.equals(SMS)) {
            // SMS gönderme simülasyonu
            System.out.println("SMS gönderiliyor...");
            System.out.println("API URL: " + smsApiUrl);
            System.out.println("Gönderen: " + smsGonderenNumara);

            // Telefon numarası kontrol
            if (alici == null || alici.length() < 10) {
                System.out.println("HATA: Geçersiz telefon numarası!");
                return false;
            }

            // SMS karakter limiti
            String smsMesaj = baslik + ": " + mesaj;
            if (smsMesaj.length() > 160) {
                smsMesaj = smsMesaj.substring(0, 157) + "...";
                System.out.println("UYARI: SMS 160 karakter sınırına kırpıldı.");
            }

            if (oncelik > 3) {
                smsMesaj = "[ACİL] " + smsMesaj;
            }

            System.out.println("SMS içeriği: " + smsMesaj);
            System.out.println("SMS başarıyla gönderildi: " + alici);
            sonuc = true;

        } else if (tip.equals(PUSH)) {
            // Push bildirim gönderme simülasyonu
            System.out.println("Push bildirim gönderiliyor...");
            System.out.println("Push Sunucu: " + pushSunucuUrl);
            System.out.println("Uygulama: " + pushUygulamaId);

            // Cihaz token kontrolü
            if (alici == null || alici.isEmpty()) {
                System.out.println("HATA: Geçersiz cihaz token'ı!");
                return false;
            }

            String pushPayload = "{";
            pushPayload += "\"title\":\"" + baslik + "\",";
            pushPayload += "\"body\":\"" + mesaj + "\",";
            pushPayload += "\"priority\":" + oncelik + ",";
            pushPayload += "\"token\":\"" + alici + "\"";
            if (oncelik > 3) {
                pushPayload += ",\"sound\":\"critical\"";
                pushPayload += ",\"badge\":1";
            }
            pushPayload += "}";

            System.out.println("Push payload: " + pushPayload);
            System.out.println("Push bildirim başarıyla gönderildi: " + alici);
            sonuc = true;

        } else {
            System.out.println("HATA: Bilinmeyen bildirim tipi: " + tip);
            return false;
        }

        // Geçmişe kaydet (tüm tipler için aynı formatta)
        if (sonuc) {
            String tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String kayit = tarih + " | " + tip + " | " + alici + " | " + baslik + " | " + 
                          (sonuc ? "BASARILI" : "BASARISIZ");
            bildirimGecmisi.add(kayit);
        }

        return sonuc;
    }

    /**
     * Toplu bildirim gönderme - yine aynı if-else yapısı tekrarlanıyor
     */
    public void topluBildirimGonder(String tip, List<String> alicilar, String baslik, String mesaj, int oncelik) {
        System.out.println("\n=== TOPLU BİLDİRİM ===");
        System.out.println("Toplam alıcı: " + alicilar.size());

        int basarili = 0;
        int basarisiz = 0;

        for (String alici : alicilar) {
            // Her alıcı için aynı dev metodu çağır
            boolean sonuc = bildirimGonder(tip, alici, baslik, mesaj, oncelik);
            if (sonuc) {
                basarili++;
            } else {
                basarisiz++;
            }

            // Rate limiting - her tip için farklı bekleme süreleri (tekrar if-else)
            if (tip.equals(EMAIL)) {
                // E-posta rate limit: 100ms
                try { Thread.sleep(100); } catch (InterruptedException e) { }
            } else if (tip.equals(SMS)) {
                // SMS rate limit: 500ms (daha pahalı)
                try { Thread.sleep(500); } catch (InterruptedException e) { }
            } else if (tip.equals(PUSH)) {
                // Push rate limit: 50ms
                try { Thread.sleep(50); } catch (InterruptedException e) { }
            }
        }

        System.out.println("Toplu gönderim tamamlandı: " + basarili + " başarılı, " + basarisiz + " başarısız");
    }

    /**
     * Bildirim formatla - her tip için farklı format (yine if-else)
     */
    public String bildirimFormatla(String tip, String baslik, String mesaj) {
        if (tip.equals(EMAIL)) {
            return "<html><head><title>" + baslik + "</title></head><body><h1>" + baslik + "</h1><p>" + mesaj + "</p></body></html>";
        } else if (tip.equals(SMS)) {
            String kisaMesaj = baslik + ": " + mesaj;
            if (kisaMesaj.length() > 160) {
                kisaMesaj = kisaMesaj.substring(0, 157) + "...";
            }
            return kisaMesaj;
        } else if (tip.equals(PUSH)) {
            return "{\"title\":\"" + baslik + "\",\"body\":\"" + mesaj + "\"}";
        }
        return mesaj;
    }

    /**
     * Bildirim doğrula - her tip için farklı doğrulama kuralları
     */
    public boolean aliciDogrula(String tip, String alici) {
        if (tip.equals(EMAIL)) {
            return alici != null && alici.contains("@") && alici.contains(".");
        } else if (tip.equals(SMS)) {
            return alici != null && alici.length() >= 10 && alici.startsWith("+");
        } else if (tip.equals(PUSH)) {
            return alici != null && alici.length() >= 20; // token en az 20 karakter
        }
        return false;
    }

    /**
     * Bildirim geçmişi - filtreleme de if-else ile
     */
    public List<String> gecmisGetir(String filtreTip) {
        if (filtreTip == null || filtreTip.isEmpty()) {
            return new ArrayList<>(bildirimGecmisi);
        }

        List<String> filtrelenmis = new ArrayList<>();
        for (String kayit : bildirimGecmisi) {
            if (kayit.contains(filtreTip)) {
                filtrelenmis.add(kayit);
            }
        }
        return filtrelenmis;
    }

    /**
     * İstatistik getir - her tip için ayrı ayrı sayıyor
     */
    public void istatistikYazdir() {
        int emailSayisi = 0;
        int smsSayisi = 0;
        int pushSayisi = 0;

        for (String kayit : bildirimGecmisi) {
            if (kayit.contains(EMAIL)) {
                emailSayisi++;
            } else if (kayit.contains(SMS)) {
                smsSayisi++;
            } else if (kayit.contains(PUSH)) {
                pushSayisi++;
            }
        }

        System.out.println("\n=== BİLDİRİM İSTATİSTİKLERİ ===");
        System.out.println("E-posta: " + emailSayisi);
        System.out.println("SMS: " + smsSayisi);
        System.out.println("Push: " + pushSayisi);
        System.out.println("Toplam: " + (emailSayisi + smsSayisi + pushSayisi));
    }

    /**
     * Ayarları güncelle - her tip için farklı ayarlar (God Class problemi)
     */
    public void smtpAyarlariGuncelle(String sunucu, int port, String kullaniciAdi, String sifre) {
        this.smtpSunucu = sunucu;
        this.smtpPort = port;
        this.smtpKullaniciAdi = kullaniciAdi;
        this.smtpSifre = sifre;
        System.out.println("SMTP ayarları güncellendi.");
    }

    public void smsAyarlariGuncelle(String apiAnahtari, String gonderenNumara, String apiUrl) {
        this.smsApiAnahtari = apiAnahtari;
        this.smsGonderenNumara = gonderenNumara;
        this.smsApiUrl = apiUrl;
        System.out.println("SMS ayarları güncellendi.");
    }

    public void pushAyarlariGuncelle(String apiAnahtari, String sunucuUrl, String uygulamaId) {
        this.pushApiAnahtari = apiAnahtari;
        this.pushSunucuUrl = sunucuUrl;
        this.pushUygulamaId = uygulamaId;
        System.out.println("Push ayarları güncellendi.");
    }

    // Getter'lar - hepsi bu sınıfta
    public String getSmtpSunucu() { return smtpSunucu; }
    public String getSmsApiUrl() { return smsApiUrl; }
    public String getPushSunucuUrl() { return pushSunucuUrl; }
}
