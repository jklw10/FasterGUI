package dev.tr7zw.fastergui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import dev.tr7zw.config.CustomConfigScreen;
import dev.tr7zw.fastergui.util.BufferRenderer;
import dev.tr7zw.fastergui.util.DefaultModel;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;

public abstract class FasterGuiModBase {

    public static FasterGuiModBase instance;
    private static boolean forceBlend, blendBypass;
    private static int bypassTurnoff;

    public Config config;
    private final File settingsFile = new File("config", "fastergui.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private BufferRenderer screenBufferRenderer;
    private RenderTarget temporaryScreenOverwrite = null;
    public static SignSettings signSettings = new SignSettings();
    public static NametagSettings nametagSettings = new NametagSettings();

    public void onInitialize() {
		instance = this;

        if (settingsFile.exists()) {
            try {
                config = gson.fromJson(new String(Files.readAllBytes(settingsFile.toPath()), StandardCharsets.UTF_8),
                        Config.class);
            } catch (Exception ex) {
                System.out.println("Error while loading config! Creating a new one!");
                ex.printStackTrace();
            }
        }
        if (config == null) {
            config = new Config();
            writeConfig();
        } else {
            if(ConfigUpgrader.upgradeConfig(config)) {
                writeConfig(); // Config got modified
            }
        }
		initModloader();
	}
	
    public void writeConfig() {
        if (settingsFile.exists())
            settingsFile.delete();
        try {
            Files.write(settingsFile.toPath(), gson.toJson(config).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    public BufferRenderer getScreenBufferRenderer() {
        if(screenBufferRenderer == null) {
            screenBufferRenderer = new BufferRenderer(true);
        }
        return screenBufferRenderer;
    }
    
    public abstract void initModloader();

    public Screen createConfigScreen(Screen parent) {
        CustomConfigScreen screen = new CustomConfigScreen(parent, "text.fastergui.title") {

            @Override
            public void initialize() {
                List<OptionInstance<?>> options = new ArrayList<>();
                options.add(getOnOffOption("text.fastergui.enableGui", () -> config.enabledGui,
                        (b) -> config.enabledGui = b));
                options.add(getIntOption("text.fastergui.targetFramerateGui", 5, 60, () -> config.targetFPSIngameGui, (v) -> config.targetFPSIngameGui = v));
                options.add(getOnOffOption("text.fastergui.enableScreen", () -> config.enabledScreens,
                        (b) -> config.enabledScreens = b));
                options.add(getIntOption("text.fastergui.targetFramerateScreen", 20, 120, () -> config.targetFPSIngameScreens, (v) -> config.targetFPSIngameScreens = v));
                options.add(getOnOffOption("text.fastergui.enableSignBuffering", () -> config.enableSignBuffering,
                        (b) -> config.enableSignBuffering = b));
                options.add(getOnOffOption("text.fastergui.enableNametagBuffering", () -> config.enableNametagBuffering,
                        (b) -> config.enableNametagBuffering = b));
              
                getOptions().addSmall(options.toArray(new OptionInstance[0]));
                
            }

            @Override
            public void save() {
                writeConfig();
            }

            @Override
            public void reset() {
                config = new Config();
                writeConfig();
            }

        };

        return screen;
    }

    public static boolean isForceBlend() {
        return forceBlend;
    }

    public static void setForceBlend(boolean forceBlend) {
        FasterGuiModBase.forceBlend = forceBlend;
    }

    public static boolean isBlendBypass() {
        return blendBypass;
    }

    public static void setBlendBypass(boolean blendBypass) {
        // force blend is on, bypass is on and we are turning it off
        if(forceBlend && FasterGuiModBase.blendBypass && !blendBypass) {
            correctBlendMode(); // fix the blend state to the expected one
        }
        FasterGuiModBase.blendBypass = blendBypass;
    }
    
    public static int getBypassTurnoff() {
        return bypassTurnoff;
    }

    public static void setBypassTurnoff(int bypassTurnoff) {
        FasterGuiModBase.bypassTurnoff = bypassTurnoff;
    }

    public static void correctBlendMode() {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    public RenderTarget getTemporaryScreenOverwrite() {
        return temporaryScreenOverwrite;
    }

    public void setTemporaryScreenOverwrite(RenderTarget temporaryScreenOverwrite) {
        this.temporaryScreenOverwrite = temporaryScreenOverwrite;
    }
    
}
