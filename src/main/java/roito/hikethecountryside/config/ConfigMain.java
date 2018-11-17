package roito.hikethecountryside.config;

import net.minecraftforge.common.config.Config;
import roito.hikethecountryside.HikeTheCountryside;

@Config(modid = HikeTheCountryside.MODID)
@Config.LangKey("teastory.config")
public final class ConfigMain
{
	@Config.Name("General")
	@Config.LangKey("config.hikethecountryside.general")
	public static final General general = new General();

	@Config.Name("Craft")
	@Config.LangKey("config.hikethecountryside.craft")
	public static final Craft craft = new Craft();

	@Config.Name("Others")
	@Config.LangKey("config.hikethecountryside.others")
	public static final Others others = new Others();

	public static final class General
	{
		@Config.Comment("Set it to false to disable the limitation of drying.")
		@Config.LangKey("config.hikethecountryside.general.limited.drying")
		@Config.Name("IsDryingLimited")
		public boolean isDryingLimited = true;

		@Config.Comment("Set it to false to disable the limitation of fermentation.")
		@Config.LangKey("config.hikethecountryside.general.limited.fermentation")
		@Config.Name("IsFermentationLimited")
		public boolean isFermentationLimited = true;
	}

	public static final class Craft
	{
		@Config.Comment("The ticks of drying per item. (1 second = 20 ticks)")
		@Config.LangKey("config.hikethecountryside.craft.dryingbasictime")
		@Config.Name("DryingBasicTime")
		@Config.RangeInt(min = 0, max = 12000)
		public int dryingBasicTime = 600;

		@Config.Comment("The ticks of fermentation per item. (1 second = 20 ticks)")
		@Config.LangKey("config.hikethecountryside.craft.fermentationbasictime")
		@Config.Name("FermentationBasicTime")
		@Config.RangeInt(min = 0, max = 12000)
		public int fermentationBasicTime = 600;
	}

	public static final class Others
	{

	}
}
