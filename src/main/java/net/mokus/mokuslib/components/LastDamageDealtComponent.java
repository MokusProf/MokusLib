package net.mokus.mokuslib.components;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class LastDamageDealtComponent implements Component, AutoSyncedComponent {
    private float lastDamageGotten;
    private float resDamageGotten;

    public LastDamageDealtComponent(LivingEntity entity) {
    }

    public float getLastDamageGotten(){
        return this.lastDamageGotten;
    }
    public void setLastDamageGotten(float value){
        this.lastDamageGotten = value;
    }

    public float getResDamageGotten(){
        return this.resDamageGotten;
    }
    public void setResDamageGotten(float value){
        this.resDamageGotten = value;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.lastDamageGotten = nbtCompound.getFloat("lastDamageGotten");
        this.resDamageGotten = nbtCompound.getFloat("resDamageGotten");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putFloat("lastDamageGotten", this.lastDamageGotten);
        nbtCompound.putFloat("resDamageGotten", this.resDamageGotten);
    }
}
