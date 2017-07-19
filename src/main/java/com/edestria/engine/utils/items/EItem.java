package com.edestria.engine.utils.items;

import com.edestria.engine.utils.lang.Lang;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EItem extends ItemStack {

    public EItem(ItemStack itemStack) {
        super(itemStack);
    }

    public EItem(Material material) {
        super(material);
    }

    public EItem withAmount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public EItem withName(String name) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(Lang.color(name));
        this.setItemMeta(itemMeta);
        return this;
    }

    public EItem withLore(String... lore) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(Arrays.stream(lore).map(Lang::color).collect(Collectors.toList()));
        this.setItemMeta(itemMeta);
        return this;
    }

    public EItem withLore(List<String> lore) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore.stream().map(Lang::color).collect(Collectors.toList()));
        this.setItemMeta(itemMeta);
        return this;
    }

    public EItem glowing() {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(itemMeta);
        return this;
    }

    public EItem withData(int data) {
        this.setDurability((short) data);
        return this;
    }

    public EItem withArmorColor(Color color) {
        if (!this.getType().name().toLowerCase().contains("leather_")) {
            return this;
        }
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.getItemMeta();
        leatherArmorMeta.setColor(color);
        leatherArmorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(leatherArmorMeta);
        return this;
    }

    public EItem withPotionColor(Color color) {
        if (!this.getType().name().toLowerCase().contains("potion")) {
            return this;
        }
        PotionMeta potionMeta = (PotionMeta) this.getItemMeta();
        potionMeta.setColor(color);
        this.setItemMeta(potionMeta);
        return this;
    }

    public EItem withPotionEffect(PotionEffect... potionEffects) {
        if (!this.getType().name().toLowerCase().contains("potion")) {
            return this;
        }
        PotionMeta potionMeta = (PotionMeta) this.getItemMeta();
        Arrays.stream(potionEffects).forEach(potionEffectType -> potionMeta.addCustomEffect(potionEffectType, true));
        this.setItemMeta(potionMeta);
        return this;
    }
}