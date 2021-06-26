package theRanger.powers.brown;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theRanger.DefaultMod;
import theRanger.actions.brown.generic.InfusePowerTurnAction;
import theRanger.util.TextureLoader;

import static theRanger.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class InfusedPower extends AbstractPower implements HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("InfusedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    static final Color COLOR = Color.CYAN;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private boolean isRefreshed;

    public InfusedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        this.isRefreshed = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        //if (amount == 1) {
        this.description = DESCRIPTIONS[0];
        //} else if (amount > 1) {
            //description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        //}
    }


    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if(damageAmount > 0 && (this.owner.currentHealth-damageAmount) <= this.amount){
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            this.addToTop(new DamageAction(this.owner,new DamageInfo(this.source, this.amount, DamageInfo.DamageType.THORNS)));
        }
        super.wasHPLost(info, damageAmount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(stackAmount > 0) this.isRefreshed = true;
        if(!this.owner.isDead && !this.owner.halfDead && !this.owner.isDying && this.owner.currentHealth <= this.amount){
            this.addToBot(new DamageAction(owner,new DamageInfo(this.source, this.amount, DamageInfo.DamageType.THORNS)));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.isRefreshed = true;
        if(!this.owner.isDead && !this.owner.halfDead && !this.owner.isDying && this.owner.currentHealth <= this.amount){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            addToTop(new DamageAction(owner,new DamageInfo(this.source, this.amount, DamageInfo.DamageType.THORNS)));
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(this.amount == 0) {
            this.owner.powers.remove(this);
        }
    }



    @Override
    public void atStartOfTurn() {
        boolean aux = this.isRefreshed;
        addToTop(new InfusePowerTurnAction(this, aux));
        this.isRefreshed = false;
    }

    @Override
    public int getHealthBarAmount() {
            return this.amount;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }
}