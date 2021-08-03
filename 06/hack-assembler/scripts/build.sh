echo Building assembler

cd ..
./gradlew clean installDist

mv -f build/install/hack-assembler/bin/hack-assembler ~/nand2tetris/tools

cd ~/nand2tetris/tools
chmod +x hack-assembler

echo Finished
