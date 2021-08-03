package com.cinema.app.infrastructure.persistence.impl;

import com.cinema.app.domain.address.Address;
import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.generic.AbstractCrudDao;
import org.jdbi.v3.core.Jdbi;

public class AddressDaoImpl extends AbstractCrudDao<Address,Long> implements AddressDao {

    protected AddressDaoImpl(Jdbi jdbi) { super(jdbi);  }
}
