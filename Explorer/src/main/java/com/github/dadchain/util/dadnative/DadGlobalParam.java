package com.github.dadchain.util.dadnative;

import com.github.ontio.io.BinaryReader;
import com.github.ontio.io.BinaryWriter;
import com.github.ontio.io.Serializable;
import com.github.ontio.io.utils;

import java.io.IOException;

public class DadGlobalParam implements Serializable {
    public long uerBonusPercent;
    public long publisherBonusPercent;
    public DadGlobalParam(){}
    public DadGlobalParam( int uerBonusPercent, int publisherBonusPercent){
        this.uerBonusPercent = uerBonusPercent;
        this.publisherBonusPercent = publisherBonusPercent;
    }

    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        this.uerBonusPercent = utils.readVarInt(reader);
        this.publisherBonusPercent = utils.readVarInt(reader);
    }

    @Override
    public void serialize(BinaryWriter binaryWriter) throws IOException {

    }


}
