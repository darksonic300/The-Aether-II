package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockAercloud extends Block implements IAetherBlockWithVariants
{
	public static class AercloudVariant extends BlockVariant
	{
		public AercloudVariant(int meta, String name)
		{
			super(meta, name);
		}

		public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
		{
			entity.fallDistance = 0;

			if (entity.motionY < 0)
			{
				entity.motionY *= 0.005D;
			}
		}

		public AxisAlignedBB getBoundingBox(BlockPos pos, double maxX, double maxY, double maxZ)
		{
			return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + maxX, pos.getY(), pos.getZ() + maxZ);
		}
	}

	public static final AercloudVariant
			COLD_AERCLOUD = new AercloudVariant(0, "aercloud_cold"),
			BLUE_AERCLOUD = new AercloudVariant(1, "aercloud_blue")
			{
				@Override
				public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
				{
					entity.motionY = 2.0D;

					super.onEntityCollision(world, pos, state, entity);
				}
			},
			GREEN_AERCLOUD = new AercloudVariant(2, "aercloud_green")
			{
				@Override
				public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
				{
					EnumFacing randomSide = EnumFacing.random(world.rand);

					entity.motionX = randomSide.getFrontOffsetX() * 2.5D;
					entity.motionZ = randomSide.getFrontOffsetZ() * 2.5D;
				}
			},
			GOLDEN_AERCLOUD = new AercloudVariant(3, "aercloud_golden")
			{
				@Override
				public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
				{
					entity.motionY = -1.5D;
				}

				@Override
				public AxisAlignedBB getBoundingBox(BlockPos pos, double maxX, double maxY, double maxZ)
				{
					return null;
				}
			},
			STORM_AERCLOUD = new AercloudVariant(4, "aercloud_storm"),
			PURPLE_AERCLOUD = new AercloudVariant(5, "aercloud_purple")
			{
				@Override
				public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
				{
					EnumFacing side = (EnumFacing) state.getValue(PROPERTY_FACING);

					entity.motionX = side.getFrontOffsetX() * 2.5D;
					entity.motionZ = side.getFrontOffsetZ() * 2.5D;
				}
			};

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", COLD_AERCLOUD, BLUE_AERCLOUD, GREEN_AERCLOUD, GOLDEN_AERCLOUD, STORM_AERCLOUD, PURPLE_AERCLOUD);

	public static final PropertyEnum PROPERTY_FACING = PropertyEnum.create("facing", EnumFacing.class);

	public BlockAercloud()
	{
		super(Material.ice);

		this.setStepSound(Block.soundTypeCloth);

		this.setHardness(0.2f);
		this.setLightOpacity(1);

		this.setCreativeTab(AetherCreativeTabs.tabBlocks);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, COLD_AERCLOUD).withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return Minecraft.isFancyGraphicsEnabled() ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		BlockPos otherBlock = pos.offset(side.getOpposite());

		if (worldIn.getBlockState(otherBlock).getBlock() == worldIn.getBlockState(pos).getBlock())
		{
			if (worldIn.getBlockState(otherBlock).getValue(PROPERTY_VARIANT) == worldIn.getBlockState(pos).getValue(PROPERTY_VARIANT))
			{
				return false;
			}
		}

		return super.shouldSideBeRendered(worldIn, pos, side);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		AercloudVariant variant = (AercloudVariant) state.getValue(PROPERTY_VARIANT);

		if (entity.isSneaking())
		{
			BlockAercloud.COLD_AERCLOUD.onEntityCollision(world, pos, state, entity);
		}
		else
		{
			variant.onEntityCollision(world, pos, state, entity);
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		AercloudVariant variant = (AercloudVariant) state.getValue(PROPERTY_VARIANT);

		return variant.getBoundingBox(pos, this.minX, this.minY, this.minZ);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= PURPLE_AERCLOUD.getMeta())
		{
			return this.getDefaultState().withProperty(PROPERTY_VARIANT, PURPLE_AERCLOUD).withProperty(PROPERTY_FACING, EnumFacing.getFront(meta - PURPLE_AERCLOUD.getMeta()));
		}

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.getVariantFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(PROPERTY_VARIANT) == PURPLE_AERCLOUD)
		{
			return PURPLE_AERCLOUD.getMeta() + ((EnumFacing) state.getValue(PROPERTY_FACING)).getIndex();
		}

		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_VARIANT, PROPERTY_FACING);
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		return PROPERTY_VARIANT.getVariantFromMeta(stack.getMetadata()).getName();
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(PROPERTY_FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(this, 1, ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta());
	}

}
