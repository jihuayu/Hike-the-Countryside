package roito.hikethecountryside.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import snownee.kiwi.block.BlockMod;

public class HCBlock extends BlockMod
{
	private boolean opaqueCube;
	private boolean fullCube;

	public HCBlock(Material materialIn, String name, CreativeTabs tab, float hardness, boolean opaqueCube, boolean fullCube)
	{
		super(name, materialIn);
		this.setHardness(hardness);
		this.setCreativeTab(tab);
		this.opaqueCube = opaqueCube;
		this.fullCube = fullCube;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return opaqueCube;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return fullCube;
	}
}
