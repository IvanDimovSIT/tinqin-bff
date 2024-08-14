package com.tinqinacademy.bff.core.conversion.converters.enums;

import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import org.springframework.stereotype.Component;

@Component
public class BedSizeToBffBedSizeConverter extends BaseConverter<BedSize, BffBedSize> {

    @Override
    protected BffBedSize convertObject(BedSize source) {
        if (source == null) {
            return null;
        }

        return BffBedSize.getCode(source.toString());
    }
}
