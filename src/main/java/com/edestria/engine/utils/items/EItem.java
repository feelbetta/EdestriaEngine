package com.edestria.engine.utils.items;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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
import java.util.stream.Collectors;

public class EItem extends ItemStack {

    public EItem(Material material) {
        super(material);
    }

    public EItem withAmount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public EItem withName(String name) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.setItemMeta(itemMeta);
        return this;
    }

    public EItem withLore(String... lore) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(Arrays.stream(lore).map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
        this.setItemMeta(itemMeta);
        return this;
    }

    public EItem glowing() {
        ItemMeta itemMeta = this.getItemMeta();
        this.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.setItemMeta(itemMeta);
        return this;
    }

    public EItem withData(int data) {
        this.setDurability((short) data);
        return this;
    }

    public EItem color(Color color) {
        if (!this.getType().name().toLowerCase().contains("leather_")) {
            return this;
        }
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.getItemMeta();
        leatherArmorMeta.setColor(color);
        leatherArmorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(leatherArmorMeta);
        return this;
    }

    public EItem withPotionEffect(PotionEffect... potionEffects) {
        if (!this.getType().name().toLowerCase().contains("potion")) {
            Bukkit.broadcastMessage("no");
            return this;
        }
        PotionMeta potionMeta = (PotionMeta) this.getItemMeta();
        Arrays.stream(potionEffects).forEach(potionEffectType -> potionMeta.addCustomEffect(potionEffectType, true));
        this.setItemMeta(potionMeta);
        return this;
    }
}