package theRanger.powers.brown;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theRanger.DefaultMod;
import theRanger.util.ShotHelper;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class EssencePower extends TwoAmountPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("EssencePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));


    public EssencePower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.amount2 = 1;
        this.owner = owner;
        this.amount = amount;
        this.priority = 2;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }

    public void updateMax(int cap){
        this.amount2 = 1 + cap;
        if(this.amount > this.amount2){
            this.amount = this.amount2;
        }
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.amount2 = 1;
        if(this.owner.hasPower(SpiritPower.POWER_ID)){

            this.amount2 += this.owner.getPower(SpiritPower.POWER_ID).amount;
        }
        if (this.amount > this.amount2){
            this.amount = this.amount2;
            ShotHelper.TriggerOvercharge();
        }
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if(this.owner.hasPower(SpiritPower.POWER_ID)){
            this.updateMax(this.owner.getPower(SpiritPower.POWER_ID).amount);
        }
        if (this.amount > this.amount2){
            this.amount = this.amount2;
            ShotHelper.TriggerOvercharge();
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        boolean check = this.amount > 0;
        super.reducePower(reduceAmount);
        if(check && this.amount == 0) {
            ShotHelper.TriggerEmptiness();
        }
    }
}