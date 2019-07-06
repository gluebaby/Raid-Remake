package me.undeadguppy.raidenchants;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rit.sucy.CustomEnchantment;

public class HasteEnchantment extends CustomEnchantment {

	static final Material[] HASTE_ITEMS = new Material[] { Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
			Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE,
			Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
			Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
			Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE,
			Material.GOLD_LEGGINGS, Material.GOLD_BOOTS };

	public HasteEnchantment() {
		super("Haste", HASTE_ITEMS, 2);

		// These are optional things you can change

		// This is the maximum level for your enchantment
		// Default is 1
		this.max = 2;

		// This changes the minimum modified level needed to get your enchant
		// On not normally enchanted items like blaze rods or pumpkin seeds, you
		// should keep it as 1
		// Default is 1
		this.base = 1;

		// This one changes how easily you can get higher tiers of the
		// enchantment
		// If you have a high maximum level, you probably want a small interval
		// Default is 10
		this.interval = 15;
	}

	@Override
	public void applyEquipEffect(Player p, int level) {
		if (level == 1) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10000, 0));
		}
		if (level == 2) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10000, 1));
		}
	}

	@Override
	public void applyUnequipEffect(Player p, int level) {
		p.removePotionEffect(PotionEffectType.FAST_DIGGING);
	}
}
