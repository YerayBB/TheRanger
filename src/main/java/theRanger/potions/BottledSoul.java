package theRanger.potions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomPotion;
import theRanger.DefaultMod;

//TODO
public class BottledSoul extends CustomPotion {

    public static final String POTION_ID = theRanger.DefaultMod.makeID("BottledSoul");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final int MAGIC = 3;

    public BottledSoul() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.SMOKE);

        labOutlineColor = DefaultMod.DEFAULT_BROWN;
        // Potency is the damage/magic number equivalent of potions.
        isThrown = false;
        //initializeData();
       /* potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.


        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));*/

    }

    public void initializeData() {
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle("theranger:Spirit"), BaseMod.getKeywordDescription("theranger:Spirit")));
        tips.add(new PowerTip(BaseMod.getKeywordTitle("theranger:Essence"), BaseMod.getKeywordDescription("theranger:Essence")));
    }

        //TODO
    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, potency), potency));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new LoseStrengthPower(target, potency), potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BottledSoul();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return MAGIC;
    }
}
