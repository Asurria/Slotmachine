package slotmachine;

/**
 * Enum des symboles de la machine à sous PokéSlot.
 * Pour ajouter un symbole, il suffit d'ajouter le nom du PNG (sans extension) et du son (sans extension).
 * Les règles de gain seront gérées séparément.
 */
public enum PokeSlotSymbols {
    ORAN_BERRY("symbol_oran_berry", "sound_oran_berry"),
    MEPO_BERRY("symbol_mepo_berry", "sound_mepo_berry"),
    POKEBALL("symbol_pokeball", "sound_pokeball"),
    GREATBALL("symbol_greatball", "sound_greatball"),
    ULTRABALL("symbol_ultraball", "sound_ultraball"),
    MASTERBALL("symbol_masterball", "sound_masterball"),
    PIKACHU("symbol_pikachu", "sound_pikachu");

    private final String textureName; // nom du fichier PNG sans extension
    private final String soundName;   // nom du fichier OGG sans extension

    PokeSlotSymbols(String textureName, String soundName) {
        this.textureName = textureName;
        this.soundName = soundName;
    }

    /**
     * @return Nom du fichier PNG (sans extension)
     */
    public String getTextureName() {
        return textureName;
    }

    /**
     * @return Nom du fichier OGG (sans extension)
     */
    public String getSoundName() {
        return soundName;
    }
}
