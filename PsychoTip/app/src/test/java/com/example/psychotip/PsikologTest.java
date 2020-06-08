package com.example.psychotip;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PsikologTest {
    private Psikolog psikolog;

    @Before
    public void setUp() {
        psikolog = new Psikolog("username", "email@mail.com", "password");
        psikolog.setNamaBank("BCA");
        psikolog.setNamaRekening("Sarah Sechan");
        psikolog.setNoRekening("911911899");
        psikolog.setNomorSipp("911");
        psikolog.setSpesialisasi("Klinik");
    }

    @Test
    public void namaBankTest() {
        assertTrue(psikolog.getNamaBank().equalsIgnoreCase("BCA"));
    }

    @Test
    public void namaRekeningTest() {
        assertTrue(psikolog.getNamaRekening().equalsIgnoreCase("Sarah Sechan"));
    }

    @Test
    public void noRekeningTest() {
        assertTrue(psikolog.getNoRekening().equalsIgnoreCase("911911899"));
    }

    @Test
    public void nomorSIPPTest() {
        assertTrue(psikolog.getNomorSipp().equalsIgnoreCase("911"));
    }

    @Test
    public void spesialisasiTest() {
        assertTrue(psikolog.getSpesialisasi().equalsIgnoreCase("Klinik"));
    }

    @Test
    public void setNamaBankTest() {
        psikolog.setNamaBank("Mandiri");
        assertTrue(psikolog.getNamaBank().equalsIgnoreCase("Mandiri"));
    }

    @Test
    public void setNamaRekeningTest() {
        psikolog.setNamaRekening("Sereh");
        assertTrue(psikolog.getNamaRekening().equalsIgnoreCase("Sereh"));
    }

    @Test
    public void SetNoRekeningTest() {
        psikolog.setNoRekening("111");
        assertTrue(psikolog.getNoRekening().equalsIgnoreCase("111"));
    }

    @Test
    public void setNomorSIPPTest() {
        psikolog.setNomorSipp("101");
        assertTrue(psikolog.getNomorSipp().equalsIgnoreCase("101"));
    }

    @Test
    public void setSpesialisasiTest() {
        psikolog.setSpesialisasi("Industrial");
        assertTrue(psikolog.getSpesialisasi().equalsIgnoreCase("Industrial"));
    }

    @Test
    public void psikologTestInstanceOfUser() {
        assertTrue(psikolog instanceof User);
    }

    @Test
    public void psikologTest() {
        assertTrue(psikolog instanceof Psikolog);
    }
}
