package dogtitor.skullapi;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class SkullAPI extends JavaPlugin
{

	public static ItemStack getFromServer(String url)
	{

		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

		if (url.isEmpty())
		{

			return head;

		}

		SkullMeta meta = (SkullMeta) head.getItemMeta();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;

		try
		{

			profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, gameProfile);

        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e)
		{

        	e.printStackTrace();

		}

		head.setItemMeta(meta);

		return head;

	}

	public static ItemStack getFromPlayerName(String player)
	{

		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

		if (player.isEmpty())
		{

			return head;

		}

		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwner(player);
		head.setItemMeta(meta);

		return head;

	}

}
