package stsjorbsmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import stsjorbsmod.memories.OnModifyMemoriesSubscriber;

public class MindGlassPower extends CustomJorbsModPower implements OnModifyMemoriesSubscriber {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(MindGlassPower.class);
    public static final String POWER_ID = STATIC.ID;

    private int damage;

    public MindGlassPower(AbstractCreature owner, int damage) {
        super(STATIC);

        this.owner = owner;
        this.damage = damage;

        this.updateDescription();
    }

    @Override
    public void onGainClarity(String id) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                        null,
                        DamageInfo.createDamageMatrix(damage, true),
                        DamageInfo.DamageType.NORMAL,
                        // TODO: More impactful and relevant FX. See FlashAtkImgEffect.loadImage() and
                        //  FlashAtkImgEffect.playSound() for usage of AttackEffect in base game.
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(this.owner, this.owner, MindGlassPower.POWER_ID));
    }

    @Override
    public void updateDescription(){
        this.description = String.format(DESCRIPTIONS[0], damage);
    }

    @Override
    public AbstractPower makeCopy() {
        return new MindGlassPower(owner, damage);
    }
}
