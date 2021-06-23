package theRanger.potions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import theRanger.DefaultMod;
import theRanger.powers.brown.InfusedPower;

import java.util.Iterator;

//TODO
public class InfusivePotion extends CustomPotion {

    public static final String POTION_ID = theRanger.DefaultMod.makeID("InfusivePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final int MAGIC = 7;

    public InfusivePotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.MOON, PotionColor.SMOKE);

        this.labOutlineColor = DefaultMod.DEFAULT_BROWN;
        this.isThrown = true;
        //this.initializeData();

       /* // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.


        tips.clear();
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));*/

    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(name, description));
        this.tips.add(new PowerTip(BaseMod.getKeywordTitle("theranger:Infused"),BaseMod.getKeywordDescription("theranger:Infused")));
    }


    @Override
    public void use(AbstractCreature target) {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

            while (var2.hasNext()) {
                AbstractMonster m = (AbstractMonster) var2.next();
                if (!m.isDeadOrEscaped()) {
                    this.addToBot(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
                }
            }

            this.addToBot(new WaitAction(0.5F));

            var2 = AbstractDungeon.getMonsters().monsters.iterator();

            while (var2.hasNext()) {
                AbstractMonster m = (AbstractMonster) var2.next();
                this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new InfusedPower(m, AbstractDungeon.player, this.potency), this.potency));
            }
        }

    }

    @Override
    public AbstractPotion makeCopy() {
        return new InfusivePotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return MAGIC;
    }
}
