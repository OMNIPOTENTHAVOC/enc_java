# File Encryption Tool

<div align="center">
<img src="https://img.shields.io/badge/Java-17+-007396?style=for-the-badge&logo=java" alt="Java">
<img src="https://img.shields.io/badge/AES--128-CBC-ff6b35?style=for-the-badge" alt="AES-128">
<img src="https://img.shields.io/badge/Swing-GUI-61dafb?style=for-the-badge&logo=swing" alt="Swing">
<img src="https://img.shields.io/badge/Crypto-Demo-fb8122?style=for-the-badge" alt="Crypto Demo">
<img src="https://img.shields.io/badge/Status-Production%20Ready-00d084?style=for-the-badge&logo=github" alt="Ready">
</div>

***

## 🎯 **Statement**
> **"Secure algorithms fail spectacularly through poor implementation."**

**This tool proves it** - AES-128 breaks completely with just 3 implementation mistakes.

***

## ✨ **Core Features**

| Feature | Status | Purpose |
|---------|--------|---------|
| 🔐 **AES-128-CBC** | ✅ Live | File encryption/decryption |
| ✅ **SHA-256** | ✅ Live | Post-decryption integrity |
| 🎨 **Swing GUI** | ✅ Live | Modern interface |
| 🧪 **Pitfall Demos** | 🚨 **CRITICAL** | Educational security fails |

***

## 🚀 **One-Click Demo**

```
📁 Double-click run.bat → GUI launches instantly
```

### **Fixed IV Leak (30 seconds)**
```
1. Browse → demo_files/test.txt
2. Password: weakpassword123456  
3. Encrypt → test.txt.enc
4. REPEAT 1-3 → test.txt.enc (2nd)
5. 💥 PROOF: IDENTICAL FILES = fixed IV leak!
```

***

## 🛑 **Security Failures Exposed**

| **Vulnerability** | **Demo** | **Impact** | **Fix** |
|-------------------|----------|------------|---------|
| **Fixed IV**<br>`"1234567890123456"` | Encrypt `test.txt` 2x | **Pattern leakage**<br>Identical plaintext → identical ciphertext | Random IV + prepend |
| **Weak Keys**<br>No PBKDF2/salt | `weakpassword123456` | **Brute-forceable**<br>128-bit from 16 chars | PBKDF2 (65k+ iters + salt) |
| **No Auth** | Tamper `.enc` | **Silent corruption** | AES-GCM |

***

## 🐧 **ECB Penguin Effect**

**Reveal AES block patterns:**
```java
// CryptoUtils.java:8
static final String ALGO = "AES/ECB/PKCS5Padding"; // ← Swap CBC→ECB
```

```
Encrypt demo_files/test.png → Red pixel grid leaks in hex viewer!
```

***

## 📊 **Integrity Validation**

```
Original → SHA-256 hash → Encrypt → Decrypt → SHA-256 → COMPARE
✅ MATCH = Untampered data
❌ FAIL = Corruption detected
```

***

## 📁 **Project Layout**

```
enc_tool/
├── run.bat                    # ← LAUNCH HERE
├── README.md                 # This file
├── .gitignore
├── demo_files/               # Test vectors
│   ├── test.txt             # Fixed IV demo
│   └── test.png             # ECB penguin
└── src/main/java/
    ├── FileEncryptionTool.java
    └── CryptoUtils.java
```

***

## 📸 **Portfolio Screenshots** (Capture These)

```
1. GUI + test.txt loaded + Encrypt button
2. Side-by-side IDENTICAL test.txt.enc files  
3. ECB test.png → Pattern visualization
4. Integrity FAIL (tampered demo)
```

***

## 🔧 **Production Fixes**

```java
✅ Random IV: SecureRandom → prepend 16 bytes
✅ Strong Keys: PBKDF2WithHmacSHA256(65536, salt)
✅ Authenticated: AES/GCM/NoPadding  
✅ File Format: IV(16) + Nonce(12) + Ciphertext + Tag(16)
```

***

<div align="center">

**🛡️ AES-128 is secure. Implementation breaks it completely.**



</div>
