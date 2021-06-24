package theRanger.relics.brown;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theRanger.DefaultMod;
import theRanger.interfaces.OnShotTrigger;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.makeRelicOutlinePath;
import static theRanger.DefaultMod.makeRelicPath;

public class HandCrossbow extends CustomRelic implements OnShotTrigger {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("HandCrossbow");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicPath("HandCrossbow_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));//TextureLoader.getTexture(makeRelicOutlinePath("HandCrossbow_relic.png"));

    private static final int EFFECT = 1;
    private boolean active;

    public HandCrossbow() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void atTurnStart() {
        this.active = true;
        this.grayscale = false;
    }

    @Override
    public void OnShot() {
        if(this.active){
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DrawCardAction(EFFECT));
            this.active = false;
            this.grayscale = true;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
