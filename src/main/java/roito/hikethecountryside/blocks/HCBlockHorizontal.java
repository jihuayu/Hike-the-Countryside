package roito.hikethecountryside.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import snownee.kiwi.block.BlockModHorizontal;

public class HCBlockHorizontal extends BlockModHorizontal
{
	private boolean opaqueCube;
	private boolean fullCube;

	public HCBlockHorizontal(Material materialIn, SoundType sound, String name, CreativeTabs tab, float hardness, boolean opaqueCube, boolean fullCube)
	{
		super(name, materialIn);
		this.setHardness(hardness);
		this.setSoundType(sound);
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
