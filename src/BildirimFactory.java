/**
 * BildirimFactory — Factory Method örüntüsü.
 * 
 * Bildirim nesnelerinin yaratılma sorumluluğunu merkezi bir
 * noktada toplar. İstemci kodu hangi somut sınıfın oluşturulacağını
 * bilmek zorunda kalmaz; sadece BildirimTipi enum'unu belirtir.
 * 
 * Yeni bir bildirim tipi eklemek için:
 * 1. Bildirim arayüzünü implement eden yeni sınıf oluştur
 * 2. BildirimTipi enum'una yeni değer ekle
 * 3. Bu factory'ye yeni case ekle
 * 
 * Böylece mevcut bildirim sınıflarına dokunmaya gerek kalmaz.
 */
public class BildirimFactory {

    /**
     * Belirtilen tipe göre uygun Bildirim nesnesi oluşturur.
     * 
     * @param tip oluşturulacak bildirim tipi
     * @return ilgili Bildirim implementasyonu
     * @throws IllegalArgumentException bilinmeyen tip için
     */
    public static Bildirim bildirimOlustur(BildirimTipi tip) {
        switch (tip) {
            case EMAIL:
                return new EmailBildirim(
                    "smtp.example.com",
                    587,
                    "admin@example.com"
                );
            case SMS:
                return new SmsBildirim(
                    "api-key-12345",
                    "+905551234567",
                    "https://sms-api.example.com/send"
                );
            case PUSH:
                return new PushBildirim(
                    "push-api-key-xyz",
                    "https://push.example.com/notify",
                    "com.example.bildirim"
                );
            default:
                throw new IllegalArgumentException("Bilinmeyen bildirim tipi: " + tip);
        }
    }
}
