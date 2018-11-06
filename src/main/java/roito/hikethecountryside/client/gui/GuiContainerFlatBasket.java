package roito.hikethecountryside.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import roito.hikethecountryside.HikeTheCountryside;
import roito.hikethecountryside.inventory.ContainerFlatBasket;

@SideOnly(Side.CLIENT)
public class GuiContainerFlatBasket extends GuiContainer
{
	private static final String TEXTURE_PATH = "textures/gui/container/gui_flat_basket.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(HikeTheCountryside.MODID, TEXTURE_PATH);
	private ContainerFlatBasket inventory;

	public GuiContainerFlatBasket(ContainerFlatBasket inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.xSize = 176;
		this.ySize = 166;
		this.inventory = inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTick);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(TEXTURE);
		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

		int totalTicks = this.inventory.getTotalTicks();
		int processTicks = this.inventory.getProcessTicks();
		int textureWidth = 0;
		if (totalTicks != 0)
		{
			textureWidth = (int) Math.ceil(24.0 * processTicks / totalTicks);
		}
		this.drawTexturedModalRect(offsetX + 76, offsetY + 31, 176, 0, textureWidth, 17);
		if (this.inventory.hasHeat())
		{
			this.drawTexturedModalRect(offsetX + 80, offsetY + 50, 176, 17, 14, 14);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String title = I18n.format("tile.hikethecountryside.flat_basket.name");
		this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
		int remainSeconds = inventory.getProcessTicks() == 0 ? 0 : (inventory.getTotalTicks() - inventory.getProcessTicks()) / 20;
		int remainMinutes = remainSeconds / 60;
		remainSeconds %= 60;

		if (this.inventory.isInRain())
		{
			String inRain = I18n.format("container.hikethecountryside.flat_basket.in_rain");
			this.fontRenderer.drawString(inRain, (this.xSize - this.fontRenderer.getStringWidth(inRain)) / 2, 70, 0x404040);
		}
		else if (this.inventory.isRaining() && inventory.getMode() == 0)
		{
			String rain = I18n.format("container.hikethecountryside.flat_basket.rain");
			this.fontRenderer.drawString(rain, (this.xSize - this.fontRenderer.getStringWidth(rain)) / 2, 70, 0x404040);
		}
		else
		{
			String selectMode = I18n.format("container.hikethecountryside.flat_basket.mode." + inventory.getMode());
			String time = I18n.format("container.hikethecountryside.flat_basket.time", remainMinutes, remainSeconds, selectMode);
			this.fontRenderer.drawString(time, (this.xSize - this.fontRenderer.getStringWidth(time)) / 2, 70, 0x404040);
		}
	}
}
