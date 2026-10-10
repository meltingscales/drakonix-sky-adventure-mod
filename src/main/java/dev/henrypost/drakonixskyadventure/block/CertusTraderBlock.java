package dev.henrypost.drakonixskyadventure.block;

import dev.henrypost.drakonixskyadventure.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Sells Certus Quartz Crystal for emeralds - AE2 only lets it be grown, this is the
 * purchase path the modpack's Certus Quartz economy needs.
 */
public class CertusTraderBlock extends Block {

	private static final ResourceLocation CERTUS_QUARTZ_CRYSTAL_ID = ResourceLocation.fromNamespaceAndPath("ae2", "certus_quartz_crystal");

	public CertusTraderBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if(!stack.is(Items.EMERALD)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		if(level.isClientSide) return ItemInteractionResult.SUCCESS;

		int price = Config.EMERALDS_PER_CERTUS_QUARTZ.get();
		if(stack.getCount() < price) {
			player.displayClientMessage(Component.literal("Needs " + price + " emeralds"), true);
			return ItemInteractionResult.FAIL;
		}

		Item certusQuartzCrystal = BuiltInRegistries.ITEM.get(CERTUS_QUARTZ_CRYSTAL_ID);
		if(certusQuartzCrystal == Items.AIR) {
			player.displayClientMessage(Component.literal("Certus Quartz Crystal not found - is AE2 installed?"), true);
			return ItemInteractionResult.FAIL;
		}

		stack.shrink(price);
		player.getInventory().add(new ItemStack(certusQuartzCrystal));
		player.displayClientMessage(Component.literal("Traded " + price + " emeralds for a Certus Quartz Crystal"), true);

		return ItemInteractionResult.SUCCESS;
	}

}
