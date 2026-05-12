/**
 * Bildirim tiplerini tanımlayan enum.
 * 
 * String sabitler yerine type-safe enum kullanılarak
 * derleme zamanı güvenliği sağlanır.
 */
public enum BildirimTipi {
    EMAIL,
    SMS,
    PUSH
}
