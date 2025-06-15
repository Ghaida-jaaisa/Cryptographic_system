# 🔐 Ghaida System - One Time Pad Encryptor

A lightweight Java-based GUI tool for secure file and folder encryption using the **One-Time Pad (OTP)** algorithm.

This system ensures maximum security by encrypting each file with a truly random key that is used **only once**, making decryption mathematically unbreakable when used correctly.

---
## 📸 Demo - Encrypt File

<p align="center">
  <img src="https://github.com/user-attachments/assets/9a0e1eb7-a072-4208-bd2d-648b92d6ecce" width="30%" />
  <img src="https://github.com/user-attachments/assets/571ab9b7-b044-4090-8d9b-029761b8bbcb" width="30%" />
  <img src="https://github.com/user-attachments/assets/98999e2a-a44c-4157-b8b8-4721697a3c23" width="30%" />
</p>

## 📸 Demo - Decrypt File

<p align="center">
  <img src="https://github.com/user-attachments/assets/db9a4a5c-d298-4c43-bc74-19c7cf6effd3" width="23%" />
  <img src="https://github.com/user-attachments/assets/44b954aa-0520-4d30-8dea-5fae17a734d2" width="23%" />
  <img src="https://github.com/user-attachments/assets/0c6125a0-55d3-43e1-bd61-a33ad95d142a" width="23%" />
  <img src="https://github.com/user-attachments/assets/e8961f45-f2d0-4996-aa8d-c89135e734cc" width="23%" />
</p>


## 📦 Features

- 🔐 **Encrypt File**: Encrypts a selected file using a one-time pad. The original file is deleted after encryption.
- 🔓 **Decrypt File**: Decrypts an encrypted `.enc` file using its corresponding `.key` file.
- 🗂️ **Encrypt Folder**: Encrypts all valid files in a selected folder (excluding `.enc` and `.key` files), and deletes originals.
- 🧊 Simple and friendly **Graphical User Interface (GUI)** using Swing.
- 🧠 Automatic key generation per file, stored separately as `.key`.

---

## 🚀 Getting Started

### Prerequisites

- Java 8 or later
- IDE like NetBeans / IntelliJ / VS Code OR command line with `javac` and `java`

---

## 🛠️ How to Compile & Run

### From IDE (e.g. NetBeans or IntelliJ)

1. Create a new Java project.
2. Add both `EncryptorGUI.java` and `OneTimePadEncryptor.java` under the same package (e.g., `javaapplication3`).
3. Run the `EncryptorGUI` main class.

### From Command Line

```bash
javac EncryptorGUI.java OneTimePadEncryptor.java
java EncryptorGUI
```

## 🧾 How It Works

### 🔐 Encryption Process

1. The user selects a file (or folder).
2. The system reads the file as a byte array.
3. A random one-time pad (OTP) key is generated — same size as the file.
4. Each byte in the file is XORed with its corresponding byte in the key.
5. The result is saved as:
   - `filename.enc` → the encrypted file.
   - `filename.key` → the secret key used for encryption.
6. The original file is **automatically deleted** for enhanced security.

### 🔓 Decryption Process

1. The user selects:
   - The `.enc` encrypted file.
   - The matching `.key` file.
2. The encrypted data and key are read as byte arrays.
3. Each byte is XORed again with the key byte (since XOR is reversible).
4. The original content is restored and saved as:
   - `filename.decrypted`

> 💡 Without the exact `.key` file, decryption is impossible — this is the nature of One-Time Pad security.


